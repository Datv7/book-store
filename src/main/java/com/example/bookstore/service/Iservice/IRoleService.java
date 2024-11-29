package com.example.bookstore.service.Iservice;

import java.util.List;

import com.example.bookstore.dto.RoleResponse;
import com.example.bookstore.entity.Role;

public interface IRoleService {
	List<RoleResponse> findAll();
	RoleResponse creatRole(String authoriztion);
	void deleteRole(int id);
}
