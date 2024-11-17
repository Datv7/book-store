package com.example.bookstore.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.bookstore.dto.UserInList;
import com.example.bookstore.dto.UserRequest;
import com.example.bookstore.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	User toUser(UserRequest userRequest);
	@Mapping(target = "roles",ignore = true)
	UserInList toUserInList(User user);
}
