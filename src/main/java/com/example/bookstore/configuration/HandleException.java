package com.example.bookstore.configuration;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.bookstore.dto.ApiRespond;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class HandleException {
	
	@ExceptionHandler(value = AppException.class)
	public ResponseEntity<ApiRespond<String>> handleAppException(AppException e){
		ErrorCode error= e.getErrorCode();
		ApiRespond<String> apiRespond=ApiRespond.<String>builder()
				.code(error.getCode())
				.massage(e.getMessage())
				.build();
		log.info(apiRespond.getMassage());
		return ResponseEntity.status(error.getHttpStatus()).body(apiRespond);
	}
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<ApiRespond<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
		ErrorCode error= ErrorCode.valueOf(e.getFieldError().getDefaultMessage());
		ApiRespond<String> apiRespond=ApiRespond.<String>builder()
				.code(error.getCode())
				.massage(error.getMassage())
				.build();
		log.info(e.getMessage());
		return ResponseEntity.status(error.getHttpStatus()).body(apiRespond);
	}
	@ExceptionHandler(value = DataIntegrityViolationException.class)
	public ResponseEntity<ApiRespond<String>> handleDataIntegrityViolationException(DataIntegrityViolationException e){
		ErrorCode errorCode=ErrorCode.INFOR_INVALID;
		ApiRespond<String> apiRespond=ApiRespond.<String>builder()
				.code(errorCode.getCode())
				.massage(errorCode.getMassage())
				.build();
		log.info(e.getMessage());
		return ResponseEntity.badRequest().body(apiRespond);
	}
	@ExceptionHandler(value = MissingServletRequestParameterException.class)
	public ResponseEntity<ApiRespond<String>> handleMissingServletRequestParameterException(MissingServletRequestParameterException e){
		ErrorCode errorCode=ErrorCode.MISSING_REQUEST_PARAMETER;
		String message=errorCode.getMassage().replaceFirst("_param_", e.getParameterName());
		message=errorCode.getMassage().replaceFirst("_type_", e.getParameterType());
		ApiRespond<String> apiRespond=ApiRespond.<String>builder()
				.code(errorCode.getCode())
				.massage("Required parameter is missing.")
				.build();
		log.info(e.getMessage());
		return ResponseEntity.badRequest().body(apiRespond);
	}
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ApiRespond<String>> handleException(Exception e){
		ErrorCode errorCode=ErrorCode.UNCATEGORIZED_EXCEPTION;
		ApiRespond<String> apiRespond=ApiRespond.<String>builder()
				.code(errorCode.getCode())
				.massage(errorCode.getMassage())
				.build();
		log.info(apiRespond.getMassage());
		e.printStackTrace();
		return ResponseEntity.status(errorCode.getHttpStatus()).body(apiRespond);
	}
}
