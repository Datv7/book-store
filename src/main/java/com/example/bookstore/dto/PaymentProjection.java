package com.example.bookstore.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface PaymentProjection {
	String getId();
	String getType();
	String getStatus();
	@JsonProperty("paymentAt")
	Date getDate();
}
