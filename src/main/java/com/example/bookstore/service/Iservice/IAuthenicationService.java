package com.example.bookstore.service.Iservice;

import com.example.bookstore.dto.AuthenicationRequest;
import com.example.bookstore.dto.UserRequest;

public interface IAuthenicationService {
	Object[] login(AuthenicationRequest authenicationRequest);
	void logout(String accessToken);
	String[] refresh(String token);
	String forgotPass(String email,String resetPasswordPage);
	int register(UserRequest userRequest);
}
