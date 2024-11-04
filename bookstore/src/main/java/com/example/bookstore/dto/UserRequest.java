package com.example.bookstore.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
	@NotBlank(message = "INFOR_EMPTY")
	private String fullName;
	@NotBlank(message = "INFOR_EMPTY")
	private String email;
	@NotBlank(message = "INFOR_EMPTY")
	private String sdt;
	@NotBlank(message = "INFOR_EMPTY")
	private String password;
}
