package com.example.bookstore.service.Iservice;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.example.bookstore.dto.AddressProjection;
import com.example.bookstore.dto.AddressRequest;
import com.example.bookstore.dto.PDWProjection;

public interface IAddressService {
	Map<String , Set<String>> gatherAddress();
	List<PDWProjection> getProvince();
	List<PDWProjection> getDistrict(int provinceId);
	List<PDWProjection> getWard(int districtId);
	
	void createAddress(AddressRequest addressRequest,String userId,boolean update,String id);
	List<AddressProjection> getAll(String userId);
	void deleteAddress(String userId,String id);
}
