package com.example.bookstore.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookstore.configuration.AppException;
import com.example.bookstore.configuration.ErrorCode;
import com.example.bookstore.dto.ApiRespond;
import com.example.bookstore.dto.AuthenicationRequest;
import com.example.bookstore.dto.UserResponse;
import com.example.bookstore.service.imp.IAuthenicationService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
public class AuthenticationController {
	@Value("${jwt.expiration.refresh}")
	private int refreshExpiration;
	
	@Autowired
	private IAuthenicationService iAuthenicationService;
	@GetMapping("/login")
	public ApiRespond<String> login(HttpServletResponse response,@Valid @RequestBody AuthenicationRequest authenicationRequest){
		String[] token=iAuthenicationService.login(authenicationRequest);
		ApiRespond<String> result=ApiRespond.<String>builder()
				.results(token[0])
				.build();
		Cookie cookie=new Cookie("refreshToken", token[1]);
		cookie.setHttpOnly(true);
		cookie.setMaxAge(refreshExpiration);
		cookie.setPath("/");
		response.addCookie(cookie);
		return result;
	}
	@GetMapping("/logout/k")
	public ApiRespond logout(HttpServletRequest request,HttpServletResponse response,@RequestBody Map<String, Object> json) {
		System.out.println("lkjsd");
		String accessToken=(String)json.get("accessToken");
		System.out.println(accessToken);
		if(accessToken==null ||accessToken.trim().isEmpty()) throw new AppException(ErrorCode.UNAUTHENTICATED);
		iAuthenicationService.logout(accessToken);
		Cookie[] cookies= request.getCookies();
		for(Cookie cookie:cookies) {
			if(cookie.getName().equals("refreshToken")) {
				cookie.setMaxAge(0);
				cookie.setHttpOnly(true);
				cookie.setPath("/");
				response.addCookie(cookie);
				
				System.out.println("refresh xoacooki"+cookie.getValue());
				break;
			}
		}
		return ApiRespond.builder().build();
	}
	@GetMapping("/refresh")
	public ApiRespond<String> refresh(HttpServletRequest request,HttpServletResponse response){
		String refreshToken=null;
		Cookie[] cookies= request.getCookies();
		for(Cookie cookie:cookies) {
			if(cookie.getName().equals("refreshToken")) {
				refreshToken=cookie.getValue();
				break;
			}
		}
		System.out.println("refresh reffresh"+refreshToken);
		log.info(refreshToken);
		if(refreshToken==null) throw new AppException(ErrorCode.UNAUTHENTICATED);
		log.info("correct");
		String[] token =iAuthenicationService.refresh(refreshToken);
		if(token[1]!=null) {
			Cookie cookie=new Cookie("refreshToken", token[1]);
			cookie.setHttpOnly(true);
			cookie.setMaxAge(refreshExpiration);
			cookie.setPath("/");
			response.addCookie(cookie);
		}
		return ApiRespond.<String>builder()
				.results(token[0])
				.build();
	}
	@GetMapping("/forgotPass")
	public ApiRespond<String> forgotPass(HttpServletResponse response,@RequestBody Map<String, Object> json){
		String fullName=(String)json.get("fullName");
		String passwordResetPageUrl=(String)json.get("passwordResetPageUrl");
		if(passwordResetPageUrl==null) passwordResetPageUrl="http://localhost:8080/forgotpass.html";
		if(fullName==null) throw new AppException(ErrorCode.USER_NOT_EXISTED);
		
		
		String resetId= iAuthenicationService.forgotPass(fullName,passwordResetPageUrl);
		Cookie cookie=new Cookie(fullName,resetId);
		cookie.setHttpOnly(true);
		cookie.setMaxAge(180);
		cookie.setPath("/");
		response.addCookie(cookie);
		return ApiRespond.<String>builder().build();
	}
	
	
}
