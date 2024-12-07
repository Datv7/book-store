package com.example.bookstore.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.bookstore.configuration.AppException;
import com.example.bookstore.configuration.ErrorCode;
import com.example.bookstore.dto.AddressProjection;
import com.example.bookstore.dto.AddressRequest;
import com.example.bookstore.dto.PDWProjection;
import com.example.bookstore.entity.Address;
import com.example.bookstore.entity.District;
import com.example.bookstore.entity.Province;
import com.example.bookstore.entity.User;
import com.example.bookstore.entity.Ward;
import com.example.bookstore.repository.AddressRepository;
import com.example.bookstore.repository.DistrictRepository;
import com.example.bookstore.repository.ProvinceRepository;
import com.example.bookstore.repository.WardRepository;
import com.example.bookstore.service.Iservice.IAddressService;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class AddressService implements IAddressService{

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private ProvinceRepository provinceRepository;
	@Autowired
	private DistrictRepository districtRepository;
	@Autowired
	private WardRepository wardRepository;
	
	@Value("${ghn.token}")
	private String ghnToken;
	@Value("${ghn.shopId}")
	private String ghnShopId;
	
	@Override
	public Map<String, Set<String>> gatherAddress() {
		Map<String , Set<String>> mapResult=new HashMap<String, Set<String>>();
		Set<String> province=new HashSet<String>();
		Set<String> district=new HashSet<String>();
		Set<String> ward=new HashSet<String>();
		
		Set<String> fixProvince=new HashSet<String>();
		Set<String> fixDistrict=new HashSet<String>();
		Set<String> fixWard=new HashSet<String>();
		
		Set<String> count=new HashSet<String>();
		
		Map<Integer, Boolean> provinces=provinceRepository.findAll().stream().collect(Collectors.toMap(e->e.getIdGHN(), e->e.isEnable()));
		Map<Integer, Boolean> districts=districtRepository.findAll().stream().collect(Collectors.toMap(e->e.getIdGHN(), e->e.isEnable()));
		Map<Integer, Boolean> wards=wardRepository.findAll().stream().collect(Collectors.toMap(e->e.getIdGHN(), e->e.isEnable()));
		
		List<Province> saveProvinces=new ArrayList<Province>();
		List<District> saveDistricts=new ArrayList<District>();
		List<Ward> saveWards=new ArrayList<Ward>();
		
		int enable=0;
		String arraynull="";
		String url="https://online-gateway.ghn.vn/shiip/public-api/master-data/province";
		UriComponentsBuilder uribuild=UriComponentsBuilder.fromHttpUrl(url);
		HttpHeaders headers=new HttpHeaders();
		headers.set("token",ghnToken);
		HttpEntity<Void> httpEntity=new HttpEntity<>(headers);
		ResponseEntity<JsonNode> responseEntity= restTemplate.exchange(uribuild.toUriString(), HttpMethod.GET, httpEntity, JsonNode.class);
		
		if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            System.out.println("province thanh cong");
            JsonNode json=responseEntity.getBody();
            if(!json.get("data").isArray()) {
            	System.out.println("array null");
//            	throw new AppException(ErrorCode.SERVICE_UNAVAILABLE);
            }
//            int i=1;
            for(JsonNode data:json.get("data")) {
            	int provinceId=data.get("ProvinceID").asInt();
            	String provinceName=data.get("ProvinceName").asText();
            	boolean isEnable=false;
            	if(data.get("IsEnable")!=null) isEnable=data.get("IsEnable").asBoolean();
            	if(!isEnable) enable++;
            	System.out.printf("province: %d,name: %s, isEnable: %b %n",provinceId,provinceName,isEnable);
            	
            	Boolean valueProvince=provinces.getOrDefault(provinceId,null);
            	if(valueProvince==null) {
            		saveProvinces.add((Province.builder()
            				.idGHN(provinceId)
            				.name(provinceName)
            				.enable(isEnable)
            				.build()));
            		provinces.put(provinceId, isEnable);
            		province.add(provinceName);
            		
            	}
            	else if(valueProvince !=isEnable) {
            		Province tempProvince=provinceRepository.findByIdGHN(provinceId).orElse(null);
            		tempProvince.setEnable(isEnable);
            		saveProvinces.add(tempProvince);
            		fixProvince.add(provinceName);
            		
            	}
            }
		}else {
			System.out.println("province that bai" ); 
			throw new AppException(ErrorCode.SERVICE_UNAVAILABLE);
		}
		provinceRepository.saveAllAndFlush(saveProvinces);
		
		//district
		for(int provinceId:provinces.keySet()) {
			
        	String url2="https://online-gateway.ghn.vn/shiip/public-api/master-data/district";
    		UriComponentsBuilder uribuild2=UriComponentsBuilder.fromHttpUrl(url2);
    		uribuild2.queryParam("province_id", provinceId);
    		ResponseEntity<JsonNode> responseEntity2= restTemplate.exchange(uribuild2.toUriString(), HttpMethod.GET, httpEntity, JsonNode.class);
    		
    		if (responseEntity2.getStatusCode().equals(HttpStatus.OK)) {
                System.out.println("district thanh cong");
                JsonNode json2=responseEntity2.getBody();
                if(!json2.get("data").isArray()) {
                	System.out.println("array district null");
                	arraynull+= " province "+provinceId;
//                	throw new AppException(ErrorCode.SERVICE_UNAVAILABLE);
                }
                for(JsonNode data2:json2.get("data")) {
                	int districtId=data2.get("DistrictID").asInt();
                	String districtName=data2.get("DistrictName").asText();
                	boolean isEnable2=false;
                	if(data2.get("IsEnable")!=null) isEnable2=data2.get("IsEnable").asBoolean();
                	
                	if(!isEnable2) enable++;
                	System.out.printf("district: %d,name: %s, isEnable: %b %n",districtId,districtName,isEnable2);
                	
                	Boolean valueDistrict=districts.getOrDefault(districtId, null);
                	if(valueDistrict==null) {
                		Province tempProvince=provinceRepository.findByIdGHN(provinceId).orElse(null);
                		saveDistricts.add(District.builder()
                				.name(districtName)
                				.idGHN(districtId)
                				.enable(isEnable2)
                				.province(Province.builder().id(tempProvince.getId()).build())
                				.build());
                		districts.put(districtId, isEnable2);
                		district.add(districtName);
                		
                	}
                	else if(valueDistrict !=isEnable2) {
                		District tempDistrict=districtRepository.findByIdGHN(districtId).orElse(null);
                		tempDistrict.setEnable(isEnable2);
                		saveDistricts.add(tempDistrict);
                		fixDistrict.add(districtName);
                		
                	}
                	
                }
    		}else {
    			System.out.println("district that bai");
    			throw new AppException(ErrorCode.SERVICE_UNAVAILABLE);
    		}
		}
		districtRepository.saveAllAndFlush(saveDistricts);
		
		//ward
		for(int districtId:districts.keySet()) {
			
        	String url3="https://online-gateway.ghn.vn/shiip/public-api/master-data/ward";
    		UriComponentsBuilder uribuild3=UriComponentsBuilder.fromHttpUrl(url3);
    		uribuild3.queryParam("district_id", districtId);
    		ResponseEntity<JsonNode> responseEntity3= restTemplate.exchange(uribuild3.toUriString(), HttpMethod.GET, httpEntity, JsonNode.class);
    		
    		if (responseEntity3.getStatusCode().equals(HttpStatus.OK)) {
                System.out.println("ward thanh cong");
                JsonNode json3=responseEntity3.getBody();
                if(!json3.get("data").isArray()) {
                	System.out.println("array null");
                	arraynull+= "district "+districtId;
//                	throw new AppException(ErrorCode.SERVICE_UNAVAILABLE);
                }
                for(JsonNode data3:json3.get("data")) {
//                	int wardCode=Integer.parseInt(data3.get("WardCode").asText());
                	int wardCode=data3.get("WardCode").asInt();
                	String wardName=data3.get("WardName").asText();
                	boolean isEnable3=false;
                	if(data3.get("IsEnable")!=null) isEnable3=data3.get("IsEnable").asBoolean();
                	
                	if(!isEnable3) enable++;
                	System.out.printf("ward: %d,name: %s, isEnable: %b %n",wardCode,wardName,isEnable3);
                	
                	Boolean valueWard=wards.getOrDefault(wardCode, null);
                	if(valueWard==null) {
                		District tempDistrict=districtRepository.findByIdGHN(districtId).orElse(null);
                		saveWards.add(Ward.builder()
                				.name(wardName)
                				.idGHN(wardCode)
                				.enable(isEnable3)
                				.district(District.builder().id(tempDistrict.getId()).build())
                				.build());
                		wards.put(wardCode, isEnable3);
                		ward.add(wardName);
                	}
                	else if(valueWard !=isEnable3) {
                		Ward tempWard=wardRepository.findByIdGHN(wardCode).orElse(null);
                		tempWard.setEnable(isEnable3);
                		saveWards.add(tempWard);
                		fixWard.add(wardName);
                		
                	}
                	
                }
    		} else {
    			System.out.println("ward that bai");
    			throw new AppException(ErrorCode.SERVICE_UNAVAILABLE);
    		}
		}
		
		wardRepository.saveAllAndFlush(saveWards);
		
		count.add("province: "+ province.size());
		count.add("district: "+ district.size());
		count.add("ward: "+ ward.size());
		
		count.add("fixProvince: "+ fixProvince.size());
		count.add("fixDistrict: "+ fixDistrict.size());
		count.add("fixWard: "+ fixWard.size());
		
		count.add("!enable: " +enable);
		count.add("null: " +arraynull);
		
		mapResult.put("fixProvince", fixProvince);
		mapResult.put("fixDistrict", fixDistrict);
		mapResult.put("fixWard", fixWard);
		
		mapResult.put("province", province);
		mapResult.put("district", district);
		mapResult.put("ward", ward);
		
		mapResult.put("count", count);
		return mapResult;
	}

	@Override
	public List<PDWProjection> getProvince() {
		// TODO Auto-generated method stub
		
		return provinceRepository.getProvince();
	}

	@Override
	public List<PDWProjection> getDistrict(int provinceId) {
		// TODO Auto-generated method stub
		return districtRepository.getDistrict(provinceId);
	}

	@Override
	public List<PDWProjection> getWard(int districtId) {
		// TODO Auto-generated method stub
		return wardRepository.getWard(districtId);
	}

	@Override
	public void createAddress(AddressRequest addressRequest,String userId,boolean update,String id) {
		// TODO Auto-generated method stub
		Address address=null;
		if(update) {
			address= addressRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.ADDRESS_NOT_EXISTED));
			if(address.getUser().getId()!=userId) throw new AppException(ErrorCode.UNAUTHORIZED);
		}else {
			address=new Address();
			address.setUser(User.builder().id(userId).build());
		}
		address.setDetail(addressRequest.getDetail());
		address.setFullName(addressRequest.getFullName());
		address.setSdt(addressRequest.getSdt());
		address.setDefault(addressRequest.isDefault());
		address.setWard(Ward.builder().id(addressRequest.getWardId()).build());
		address.setDistrict(District.builder().id(addressRequest.getDistrictId()).build());
		address.setProvince(Province.builder().id(addressRequest.getProvinceId()).build());
		
		addressRepository.save(address);
		
	}

	@Override
	public List<AddressProjection> getAll(String userId) {
		// TODO Auto-generated method stub
		return addressRepository.getAll(userId, false);
	}

	@Override
	public void deleteAddress(String userId, String id) {
		// TODO Auto-generated method stub
		Address address= addressRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.ADDRESS_NOT_EXISTED));
		if(address.getUser().getId()!=userId) throw new AppException(ErrorCode.UNAUTHORIZED);
		try {
			addressRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			// TODO: handle exception
			System.out.println(" address setdelete");
			address.setDeleted(true);
			addressRepository.save(address);
		}
	}

	

}
