package com.example.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;

public interface AddressProjection {
	String getId();
	String getSdt();

	String getDetail();

	String getFullName();
	
	boolean isDefault();
	PDWProjection getProvince();
	PDWProjection getDistrict();
	PDWProjection getWard();
}
