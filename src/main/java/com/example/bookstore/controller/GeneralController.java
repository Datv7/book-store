package com.example.bookstore.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookstore.dto.ApiRespond;
import com.example.bookstore.dto.PDWProjection;
import com.example.bookstore.service.Iservice.IAddressService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.Builder;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
public class GeneralController {
	@Autowired
	private IAddressService iAddressService;
	
	
	@PostMapping("addresses/gather-address")
	public ApiRespond<Map<String , Set<String>>> gatherAddress(){
		
        return ApiRespond.<Map<String,Set<String>>>builder()
				.results(iAddressService.gatherAddress())
				.build();
	}
	
	@GetMapping("/provinces")
	public ApiRespond<List<PDWProjection>> getProvince(){
		return ApiRespond.<List<PDWProjection>>builder()
				.results(iAddressService.getProvince())
				.build();
	}
	
	@GetMapping("/districts")
	public ApiRespond<List<PDWProjection>> getDistrict(@RequestParam("provinceId") int provinceId){
		return ApiRespond.<List<PDWProjection>>builder()
				.results(iAddressService.getDistrict(provinceId))
				.build();
	}
	
	@GetMapping("/wards")
	public ApiRespond<List<PDWProjection>> getWard(@RequestParam("districtId") int districtId){
		return ApiRespond.<List<PDWProjection>>builder()
				.results(iAddressService.getWard(districtId))
				.build();
	}
	
}
