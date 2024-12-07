package com.example.bookstore.configuration;

import java.util.Set;

public class AppException extends RuntimeException{
	
	private ErrorCode errorCode;

	public AppException(ErrorCode errorCode) {
		super(errorCode.getMassage());
		this.errorCode = errorCode;
	}
	public AppException(ErrorCode errorCode,Set<String> value) {
		super(message(errorCode.getMassage(), value));
		this.errorCode = errorCode;
	}
	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
	public static String message(String message,Set<String> value) {
		for(String v:value) {
			message= message.replaceFirst("_key_", v);
		}
		return message;
	}
	
}
