package com.example.bookstore.configuration;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

	UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
	
	UNAUTHENTICATED(1001, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1002, "You do not have permission", HttpStatus.FORBIDDEN),
    TOKEN_GENERATION_ERROR(1003, "Token generate fail", HttpStatus.INTERNAL_SERVER_ERROR),
    KEY_INVALID(1004, "Key invalid", HttpStatus.FORBIDDEN),
    SERVICE_UNAVAILABLE(1005,"Service unavailable",HttpStatus.SERVICE_UNAVAILABLE),
    
    ROLE_EXISTED(1100, "Role existed", HttpStatus.BAD_REQUEST),
    ROLE_EMPTY(1101, "Role empty", HttpStatus.BAD_REQUEST),
    ROLE_NOT_EXISTED(1102, "Role not existed", HttpStatus.NOT_FOUND),
    
    ITEM_NOT_EXISTED(1103, "Item not existed", HttpStatus.NOT_FOUND),
    
    USER_EXISTED(1104, "User existed", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1105, "User not existed", HttpStatus.NOT_FOUND),
    
    INFOR_EMPTY(1106, "Field cannot be null or empty", HttpStatus.BAD_REQUEST),
    INFOR_INVALID(1107, "Field invalid", HttpStatus.BAD_REQUEST),
    
    CATEGORY_NOT_EXISTED(1108, "Category not existed", HttpStatus.NOT_FOUND),
	

	;
    
    
    
	
	private ErrorCode(int code, String massage, HttpStatus httpStatus) {
		this.code = code;
		this.massage = massage;
		this.httpStatus = httpStatus;
	}
	private int code;
	private String massage;
	private HttpStatus httpStatus;
	public int getCode() {
		return code;
	}
	public String getMassage() {
		return massage;
	}
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	
}
