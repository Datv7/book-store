package com.example.bookstore.service.Iservice;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.bookstore.dto.AUpdateUserRequest;
import com.example.bookstore.dto.PageCustom;
import com.example.bookstore.dto.PasswordResetRequest;
import com.example.bookstore.dto.UserInList;
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
	void updateUserByAdmin(String id,AUpdateUserRequest aUpdateUserRequest);
	void deleteUser(String id);
	PageCustom<UserInList> getAll(int page,int size);
}
