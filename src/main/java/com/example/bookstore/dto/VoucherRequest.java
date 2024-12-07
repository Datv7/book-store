package com.example.bookstore.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoucherRequest {
	@NotBlank(message = "INFOR_EMPTY")
	private String type;
	
	@Min(value = 0,message = "INFOR_INVALID")
	@JsonProperty("discountValue")
	private int value;
	
	@Min(value = 0,message = "INFOR_INVALID")
	@JsonProperty("usageLimit")
	private int limitCount;
	
	@JsonProperty("startDate")
	private Date startAt;
	
	@JsonProperty("endDate")
	private Date endAt;
}
