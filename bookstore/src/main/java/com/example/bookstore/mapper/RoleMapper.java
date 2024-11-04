package com.example.bookstore.mapper;

import org.mapstruct.Mapper;

import com.example.bookstore.dto.RoleResponse;
import com.example.bookstore.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
	RoleResponse toRoleResponse(Role role); 
}
