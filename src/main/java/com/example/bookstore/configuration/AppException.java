package com.example.bookstore.configuration;

public class AppException extends RuntimeException{
	
	private ErrorCode errorCode;

	public AppException(ErrorCode errorCode) {
		super(errorCode.getMassage());
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
	
	
}
