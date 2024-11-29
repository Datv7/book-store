package com.example.bookstore.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookstore.configuration.AppException;
import com.example.bookstore.configuration.ErrorCode;
import com.example.bookstore.dto.ApiRespond;
import com.example.bookstore.dto.RoleResponse;
import com.example.bookstore.service.Iservice.IRoleService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/roles")
public class RoleController {
	@Autowired
	private IRoleService iRoleService;

	@GetMapping("")
	public ApiRespond<List<RoleResponse>> findAll(){

		ApiRespond<List<RoleResponse>> result = 
				ApiRespond.<List<RoleResponse>>builder()
				.results(iRoleService.findAll())
				.build();
		return result;
	}
	@PostMapping("")
	public ApiRespond<RoleResponse> creatRole( @RequestBody Map<String, Object> json){
		String authorization=(String)json.get("authorization");
		if(authorization==null||authorization.trim().isEmpty()) throw new AppException(ErrorCode.INFOR_EMPTY);
		ApiRespond<RoleResponse> result= ApiRespond.<RoleResponse>builder()
				.results(iRoleService.creatRole(authorization))
				.build();
		return result;
	}
	@DeleteMapping("/{id}")
	public ApiRespond<Object> deleteRole(@PathVariable int id){
		iRoleService.deleteRole(id);
		ApiRespond<Object> result= ApiRespond.builder()
				.build();
		return result;
	}
}
