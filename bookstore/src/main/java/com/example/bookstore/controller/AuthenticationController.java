package com.example.bookstore.controller;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.example.bookstore.service.RedisService;
import com.example.bookstore.service.Iservice.IAuthenicationService;
import com.example.bookstore.service.Iservice.IUserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequestMapping("/auth")
@SecurityRequirement(name = "Bearer Authentication")
public class AuthenticationController {
	@Value("${jwt.expiration.refresh}")
	private int refreshExpiration;
	@Value("${expiration.otp}")
	private int expirationOtp;
	@Value("${expiration.reset_password}")
	private int resetPasswordExpiration;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private IUserService iUserService;
	@Autowired
	private IAuthenicationService iAuthenicationService;
	
	@PostMapping("/register")
	public ApiRespond<String> register(@Valid @RequestBody UserRequest userRequest){
		int otp=iAuthenicationService.register(userRequest);
		
		redisService.value.set("user:pending:"+userRequest.getEmail(), userRequest,expirationOtp,TimeUnit.SECONDS);	
		redisService.value.set("otp:email:"+userRequest.getEmail(), otp, expirationOtp, TimeUnit.SECONDS);
//	)
		return ApiRespond.<String>builder().build();
	}
	@GetMapping("/verify-otp")
	public ApiRespond<String> verifyOtp(
			@RequestParam(name = "otp") int otp,
			@NotBlank(message = "INFOR_EMPTY") @RequestParam(name = "email") String email){
		
		if( ((int)redisService.value.get("otp:email:"+email)) ==otp) {
			UserRequest userRequest=(UserRequest)redisService.value.get("user:pending:"+email);
			iUserService.creatUser(userRequest);
			redisService.template.delete("otp:email:"+email);
			redisService.template.delete("user:pending:"+email);
			return ApiRespond.<String>builder().build();
		}
		throw new AppException(ErrorCode.KEY_INVALID);
	}
	
	@PostMapping("/login")
	public ApiRespond<UserResponse> login(HttpServletResponse response,@Valid @RequestBody AuthenicationRequest authenicationRequest){
		String[] token=iAuthenicationService.login(authenicationRequest);
		UserResponse userResponse=new UserResponse(token[0],token[2]);
		ApiRespond<UserResponse> result=ApiRespond.<UserResponse>builder()
				.results(userResponse)
				.build();
		Cookie cookie=new Cookie("refreshToken", token[1]);
		cookie.setHttpOnly(true);
		cookie.setMaxAge(refreshExpiration);
		cookie.setPath("/");
		response.addCookie(cookie);
		return result;
	}
	@PostMapping("/logout")
	public ApiRespond<String> logout(HttpServletRequest request,HttpServletResponse response,@RequestBody Map<String, Object> json) {
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
				
				break;
			}
		}
		return ApiRespond.<String>builder().build();
	}
	@GetMapping("/refresh-token")
	public ApiRespond<UserResponse> refresh(HttpServletRequest request,HttpServletResponse response){
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
		return ApiRespond.<UserResponse>builder()
				.results(new UserResponse(token[0],null))
				.build();
	}
	@PostMapping("/forgot-password")
	public ApiRespond<String> forgotPass(HttpServletResponse response,@RequestBody Map<String, Object> json){
		String email=(String)json.get("email");
		String resetPasswordPage=(String)json.get("resetPasswordPage");
		if(resetPasswordPage==null) resetPasswordPage="http://localhost:8080/forgotpass.html";
		if(email==null) throw new AppException(ErrorCode.USER_NOT_EXISTED);
		
		
		String token= iAuthenicationService.forgotPass(email,resetPasswordPage);
		redisService.value.set("token:email:"+email, token,resetPasswordExpiration,TimeUnit.SECONDS);
		return ApiRespond.<String>builder().build();
	}
	@PostMapping("/reset-password")
	public ApiRespond<String> resetAsForgot(HttpServletRequest request,HttpServletResponse response,
			@Valid @RequestBody AuthenicationRequest authenicationRequest,
			@NotBlank(message = "INFOR_EMPTY") @RequestParam(name ="token" ) String token,
			@RequestParam(name ="logoutall" ) boolean logoutAll) {
		if(
			(redisService.value.get("token:email:"+authenicationRequest.getIdentifier()))
			.equals(token)){
			iUserService.resetByKey(authenicationRequest,logoutAll);
			redisService.template.delete("token:email:"+authenicationRequest.getIdentifier());
			return ApiRespond.<String>builder().build();
		}
		
		throw new AppException(ErrorCode.KEY_INVALID);
	}
	
	
}
