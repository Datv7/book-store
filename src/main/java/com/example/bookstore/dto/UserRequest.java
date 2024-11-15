package com.example.bookstore.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
	@NotBlank(message = "INFOR_EMPTY")
	private String fullName;
	@NotBlank(message = "INFOR_EMPTY")
	private String email;
	@NotBlank(message = "INFOR_EMPTY")
	private String password;
	@NotBlank(message = "INFOR_EMPTY")
	private String phoneNumber;
}
