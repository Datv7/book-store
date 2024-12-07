package com.example.bookstore.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AUpdateUserRequest {
	private List<String> roles;
	@Builder.Default
	private int version=0;
}
