package com.example.bookstore.service.imp;

import com.example.bookstore.dto.AuthenicationRequest;
import com.example.bookstore.dto.UserRequest;
import com.example.bookstore.dto.UserResponse;

public interface IUserService {
	UserResponse creatUser(UserRequest userRequest);
	void creatAdmin();
	void resetByKey(AuthenicationRequest authenicationRequest,boolean logoutAll);
}
