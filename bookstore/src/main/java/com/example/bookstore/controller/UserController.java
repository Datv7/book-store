package com.example.bookstore.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookstore.configuration.AppException;
import com.example.bookstore.configuration.ErrorCode;
import com.example.bookstore.dto.ApiRespond;
import com.example.bookstore.dto.AuthenicationRequest;
import com.example.bookstore.dto.UserRequest;
import com.example.bookstore.dto.UserResponse;
import com.example.bookstore.service.imp.IUserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private IUserService iUserService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@PostMapping("/register")
	public ApiRespond<UserResponse> creatUser(@Valid @RequestBody UserRequest userRequest){
		ApiRespond<UserResponse> result=ApiRespond.<UserResponse>builder()
				.results(iUserService.creatUser(userRequest))
				.build();
		return result;
	}
	@GetMapping("/resetPass")
	public ResponseEntity<ApiRespond<String>> resetAsForgot(HttpServletRequest request,HttpServletResponse response,
			@Valid @RequestBody AuthenicationRequest authenicationRequest,
			@RequestParam(name ="key" ) String key,
			@RequestParam(name ="logoutall" ) boolean logoutAll) {
		
		
		Cookie[] cookies= request.getCookies();
		for(Cookie cookie:cookies) {
			if(cookie.getName().equals(authenicationRequest.getFullName())) {
				if(passwordEncoder.matches(key, cookie.getValue())) {
					iUserService.resetByKey(authenicationRequest,logoutAll);
					cookie.setMaxAge(0);
					cookie.setHttpOnly(true);
					cookie.setPath("/");
					response.addCookie(cookie);
					return ResponseEntity.ok().body(null);
				}
			}
		}
		
		return ResponseEntity.badRequest().body(ApiRespond.<String>builder()
				.code(ErrorCode.KEY_INVALID.getCode())
				.massage(ErrorCode.KEY_INVALID.getMassage())
				.build());
	}
	@GetMapping("/cus")
	public String d() {
		return "customer";
	}
	@GetMapping("/admin")
	public String d1() {
		return "admin";
	}
}
