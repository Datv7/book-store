package com.example.bookstore.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookstore.dto.AUpdateUserRequest;
import com.example.bookstore.dto.ApiRespond;
import com.example.bookstore.dto.UserRequest;
import com.example.bookstore.dto.PageCustom;
import com.example.bookstore.dto.ProfileProjection;
import com.example.bookstore.dto.UserDetail;
import com.example.bookstore.entity.LogoutToken;
import com.example.bookstore.service.RedisService;
import com.example.bookstore.service.Iservice.IUserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;


@SecurityRequirement(name = "Bearer Authentication")
@RestController
//@RequestMapping("/users")
public class UserController {
	@Autowired
	private IUserService iUserService;
	@Autowired
	private RedisService redisService;
	
	@GetMapping("/generate")
	public ApiRespond<List<String>> genUser(@RequestParam(defaultValue = "10",required = false,name = "quantity") int  quantity){
		
		ApiRespond<List<String>> result=ApiRespond.<List<String>>builder()
				.results(iUserService.genUser(quantity))
				.build();
		return result;
	}
	
	@PutMapping("/admin/users/{id}")
	public ApiRespond<String> updateUserByAdmin(@PathVariable(name = "id") String id,@RequestBody AUpdateUserRequest aUpdateUserRequest){
		
		iUserService.updateUserByAdmin(id, aUpdateUserRequest);
		ApiRespond<String> result=ApiRespond.<String>builder()
				.build();
		return result;
	}
	@GetMapping("/admin/users")
	public ApiRespond<List<UserDetail>> getAll(){
		return ApiRespond.<List<UserDetail>>builder()
				.results(iUserService.getAll())
				.build();
	}
//	@GetMapping("/admin/users")
//	public ApiRespond<List<UserDetail>> getAll(){
//		return ApiRespond.<List<UserDetail>>builder()
//				.results(iUserService.getAll())
//				.build();
//	}
	@PutMapping("/admin/users/toggle-ban")
	public ApiRespond<String>  deleteUser(@RequestBody Map<String, Object> json){
		List<String> list =(ArrayList<String>)json.get("userIds");
		boolean deleted=(boolean)json.get("banned");
		iUserService.deleteUser(list,deleted);
		return ApiRespond.<String>builder()
				.build();
	}
	@GetMapping("user/profile")
	public ApiRespond<ProfileProjection> getBaseUser(Authentication authentication){
		String id=authentication.getName();
		
		return ApiRespond.<ProfileProjection>builder().results(iUserService.getProfile(id)).build();
	}
	@PutMapping("user/profile")
	public ApiRespond<String> updateProfile(Authentication authentication,@RequestBody ProfileProjection profileProjection){
		String id=authentication.getName();
		iUserService.updateProfile(id, profileProjection);
		return ApiRespond.<String>builder().build();
	}
	@PutMapping("user/password")
	public ApiRespond<ProfileProjection> changePassword(Authentication authentication,
			@RequestBody Map<String, Object> json,
			@RequestParam("logout-all") boolean logoutAll){
		String id=authentication.getName();
		String currentPass=(String)json.get("currentPassword");
		String newPass=(String)json.get("newPassword");
		
		iUserService.changePassword(id,currentPass,newPass ,logoutAll);
		return ApiRespond.<ProfileProjection>builder().build();
	}
	
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
