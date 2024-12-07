package com.example.bookstore.dto;

import com.example.bookstore.entity.District;
import com.example.bookstore.entity.Province;
import com.example.bookstore.entity.Ward;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class AddressRequest {
	@NotBlank(message = "INFOR_EMPTY")
	@JsonProperty("phoneNumber")
	private String sdt;
	@NotBlank(message = "INFOR_EMPTY")
	@JsonProperty("description")
	private String detail;
	@NotBlank(message = "INFOR_EMPTY")
	private String fullName;
	
	private boolean isDefault;
	private int districtId;
	
	private int provinceId;

	private int wardId;
}
