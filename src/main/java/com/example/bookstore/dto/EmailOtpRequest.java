package com.example.bookstore.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailOtpRequest {
	@NotBlank(message = "INFOR_EMPTY")
	private String email;
	
	private int otp;
}
