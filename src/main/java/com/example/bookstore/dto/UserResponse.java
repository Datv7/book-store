package com.example.bookstore.dto;

import java.util.List;

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
public class UserResponse {
	private String accessToken;
	private String id;
	private String fullName;
	private String email;
	private String urlAvatar;
	private List<String> roles;
}
