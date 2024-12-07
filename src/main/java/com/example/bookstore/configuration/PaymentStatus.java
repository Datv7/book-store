package com.example.bookstore.configuration;

public enum PaymentStatus {
	UNPAID("PAID"),
	PAID("UNPAID"),
	REFUNDED("PAID")
	
	;
	
	private String beforeStatus;
	private PaymentStatus(String beforeStatus) {
		this.beforeStatus=beforeStatus;
	}
	public String getBeforeStatus() {
		return beforeStatus;
	}
	
}
