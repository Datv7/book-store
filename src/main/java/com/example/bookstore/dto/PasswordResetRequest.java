package com.example.bookstore.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Builder
public class PasswordResetRequest {
	@NotBlank(message = "INFOR_EMPTY")
	private String email ;
	@NotBlank(message = "INFOR_EMPTY")
	private String password;
}
