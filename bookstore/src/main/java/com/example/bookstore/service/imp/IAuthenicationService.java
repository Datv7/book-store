package com.example.bookstore.service.imp;

import com.example.bookstore.dto.AuthenicationRequest;

public interface IAuthenicationService {
	String[] login(AuthenicationRequest authenicationRequest);
	void logout(String accessToken);
	String[] refresh(String token);
	String forgotPass(String fullName,String passwordResetPageUrl);
}
