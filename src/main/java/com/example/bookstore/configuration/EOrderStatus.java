package com.example.bookstore.configuration;

public enum EOrderStatus {
	PROCESSING("PENDING"),
	CANCELLED("PENDING"),
	SHIPPING("PACKING"),
	DELIVERED("SHIPPING"),
	RETURNED("SHIPPING"),
	
	;
	
	private String beforeStatus;
	private EOrderStatus(String beforeStatus) {
		this.beforeStatus=beforeStatus;
	}
	public String getBeforeStatus() {
		return beforeStatus;
	}
	
}
