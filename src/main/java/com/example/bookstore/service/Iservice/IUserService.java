package com.example.bookstore.service.Iservice;

import java.util.List;

import com.example.bookstore.dto.PasswordResetRequest;
import com.example.bookstore.dto.UserRequest;
import com.example.bookstore.entity.User;

public interface IUserService {
	User creatUser(UserRequest userRequest);
	void creatAdmin();
	void resetByKey(PasswordResetRequest passwordResetRequest,boolean logoutAll);
	User findUser(String identifier);
	User findUserByEmail(String email);
	List<String> genUser(int quantity);
	UserRequest randomUser();
}
