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
public class UserInList {
	private String id;
	private String fullName;
	private String email;
	private List<String> roles;
	private String phoneNumber;
	private int version;
	
}
