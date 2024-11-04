package com.example.bookstore.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenicationRequest {
	
	@NotBlank(message = "INFOR_EMPTY")
	private String fullName;
	@NotBlank(message = "INFOR_EMPTY")
	private String password;
}
