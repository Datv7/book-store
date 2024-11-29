package com.example.bookstore.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookstore.dto.ApiRespond;
import com.example.bookstore.dto.UserRequest;
import com.example.bookstore.dto.UserResponse;
import com.example.bookstore.entity.LogoutToken;
import com.example.bookstore.service.RedisService;
import com.example.bookstore.service.Iservice.IUserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;


@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private IUserService iUserService;
	@Autowired
	private RedisService redisService;
	
	
	@GetMapping("/cus")
	public String d() {
		return "customer";
	}
	@GetMapping("/admin")
	public String d1() {
		return "admin";
	}
	@GetMapping("/test")
	public String d1s() {
		redisService.value.set("tu", new LogoutToken("qw",new Date()));
		return "tesst";
	}
}
