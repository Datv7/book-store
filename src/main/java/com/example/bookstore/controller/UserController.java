package com.example.bookstore.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookstore.dto.AUpdateUserRequest;
import com.example.bookstore.dto.ApiRespond;
import com.example.bookstore.dto.PageCustom;
import com.example.bookstore.dto.UserInList;
import com.example.bookstore.entity.LogoutToken;
import com.example.bookstore.service.RedisService;
import com.example.bookstore.service.Iservice.IUserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;


@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private IUserService iUserService;
	@Autowired
	private RedisService redisService;
	
	@GetMapping("/generate")
	public ApiRespond<List<String>> gatherCategory(@RequestParam(defaultValue = "10",required = false,name = "quantity") int  quantity){
		
		ApiRespond<List<String>> result=ApiRespond.<List<String>>builder()
				.results(iUserService.genUser(quantity))
				.build();
		return result;
	}
	
	@PatchMapping("/{id}")
	public ApiRespond<String> updateUserByAdmin(@PathVariable(name = "id") String id,@RequestBody AUpdateUserRequest aUpdateUserRequest){
		
		iUserService.updateUserByAdmin(id, aUpdateUserRequest);
		ApiRespond<String> result=ApiRespond.<String>builder()
				.build();
		return result;
	}
	@GetMapping("")
	public ApiRespond<PageCustom<UserInList>> getAll(@RequestParam int page,@RequestParam int size  ){
		return ApiRespond.<PageCustom<UserInList>>builder()
				.results(iUserService.getAll(page, size))
				.build();
	}
	@DeleteMapping("/{id}")
	public ApiRespond<String>  deleteUser(@PathVariable(name = "id") String id){
		iUserService.deleteUser(id);
		return ApiRespond.<String>builder()
				.build();
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
