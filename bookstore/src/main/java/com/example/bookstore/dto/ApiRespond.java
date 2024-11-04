package com.example.bookstore.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class ApiRespond<T> {
	@Builder.Default
	private int code=1000;
	private String massage;
	private T results;
	public int getCode() {
		return code;
	}
	public String getMassage() {
		return massage;
	}
	public T getResults() {
		return results;
	}
	
	
}
