package com.example.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookstore.dto.AddressProjection;
import com.example.bookstore.dto.AddressRequest;
import com.example.bookstore.dto.ApiRespond;
import com.example.bookstore.service.Iservice.IAddressService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
public class CustomerController {
	@Autowired
	private IAddressService iAddressService;
	
	@PostMapping("/user/addresses")
	public ApiRespond<String> createAddress(Authentication authentication,@Valid @RequestBody AddressRequest  addressRequest){
		String userId=authentication.getName();
		iAddressService.createAddress(addressRequest,userId,false,null);
		return ApiRespond.<String>builder().build();
	}
	@PutMapping("/user/addresses/{id}")
	public ApiRespond<String> createAddress(Authentication authentication,@Valid @RequestBody AddressRequest  addressRequest,
			@PathVariable("id") String id){
		String userId=authentication.getName();
		iAddressService.createAddress(addressRequest,userId,true,id);
		return ApiRespond.<String>builder().build();
	}
	@GetMapping("/user/addresses")
	public ApiRespond<List<AddressProjection>> getAll(Authentication authentication){
		String userId=authentication.getName();
		
		return ApiRespond.<List<AddressProjection>>builder()
				.results(iAddressService.getAll(userId))
				.build();
	}
	@DeleteMapping("/user/addresses/{id}")
	public ApiRespond<String> deleteAddress(Authentication authentication,@PathVariable("id") String id){
		String userId=authentication.getName();
		iAddressService.deleteAddress(userId,id);
		return ApiRespond.<String>builder().build();
	}
	
	
}
