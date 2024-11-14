package com.example.bookstore.service.Iservice;

import com.example.bookstore.dto.PasswordResetRequest;
import com.example.bookstore.dto.UserRequest;
import com.example.bookstore.entity.User;

public interface IUserService {
	void creatUser(UserRequest userRequest);
	void creatAdmin();
	void resetByKey(PasswordResetRequest passwordResetRequest,boolean logoutAll);
	User findUser(String identifier);
	User findUserByEmail(String email);
}
