package com.example.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookstore.configuration.AppException;
import com.example.bookstore.configuration.ErrorCode;
import com.example.bookstore.dto.RoleResponse;
import com.example.bookstore.entity.Role;
import com.example.bookstore.mapper.RoleMapper;
import com.example.bookstore.repository.RoleRepository;
import com.example.bookstore.service.Iservice.IRoleService;
@Service
public class RoleService implements IRoleService{
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private RoleMapper roleMapper;
	@Override
	public List<RoleResponse> findAll() {
		// TODO Auto-generated method stub
		return roleRepository.findAll().stream().map(role->roleMapper.toRoleResponse(role)).toList();
	}

	@Override
	public RoleResponse creatRole(String authoriztion) {
		// TODO Auto-generated method stub
		if(roleRepository.existsByAuthorization(authoriztion)) throw new AppException(ErrorCode.ROLE_EXISTED);
		
		Role role=Role.builder().authorization(authoriztion).build();
		role=roleRepository.save(role);
		return roleMapper.toRoleResponse(role);
	}

	@Override
	public void deleteRole(int id) {
		if(!roleRepository.existsById(id)) throw new AppException(ErrorCode.ROLE_NOT_EXISTED);
		// TODO Auto-generated method stub
		roleRepository.deleteById(id);
	}

}
