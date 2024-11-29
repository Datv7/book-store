package com.example.bookstore.configuration;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.bookstore.dto.ApiRespond;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class HandleException {
	
	@ExceptionHandler(value = AppException.class)
	public ResponseEntity<ApiRespond> handleAppException(AppException e){
		ErrorCode error= e.getErrorCode();
		ApiRespond apiRespond=ApiRespond.builder()
				.code(error.getCode())
				.massage(error.getMassage())
				.build();
		log.info(apiRespond.getMassage());
		return ResponseEntity.status(error.getHttpStatus()).body(apiRespond);
	}
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<ApiRespond> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
		ErrorCode error= ErrorCode.valueOf(e.getFieldError().getDefaultMessage());
		ApiRespond apiRespond=ApiRespond.builder()
				.code(error.getCode())
				.massage(error.getMassage())
				.build();
		log.info(apiRespond.getMassage());
		return ResponseEntity.status(error.getHttpStatus()).body(apiRespond);
	}
}
