package com.example.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface VoucherProjection {
	String getType();
	String getCode();
	@JsonProperty("discountValue")
	int getValue();
}
