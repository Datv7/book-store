package com.example.bookstore.mapper;

import org.mapstruct.Mapper;

import com.example.bookstore.dto.UserRequest;
import com.example.bookstore.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	User toUser(UserRequest userRequest);
}
