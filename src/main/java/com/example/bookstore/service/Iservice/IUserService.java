package com.example.bookstore.service.Iservice;

import com.example.bookstore.dto.AuthenicationRequest;
import com.example.bookstore.dto.UserRequest;
import com.example.bookstore.entity.User;

public interface IUserService {
	void creatUser(UserRequest userRequest);
	void creatAdmin();
	void resetByKey(AuthenicationRequest authenicationRequest,boolean logoutAll);
	User findUser(String identifier);
	User findUserByEmail(String email);
}