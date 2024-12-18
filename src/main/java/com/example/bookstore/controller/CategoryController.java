package com.example.bookstore.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookstore.configuration.AppException;
import com.example.bookstore.configuration.ErrorCode;
import com.example.bookstore.dto.ApiRespond;
import com.example.bookstore.dto.CategoryResponse;
import com.example.bookstore.dto.RoleResponse;
import com.example.bookstore.entity.Category;
import com.example.bookstore.service.Iservice.ICategoryService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

//@RequestMapping("/categories")
@SecurityRequirement(name = "Bearer Authentication")
@RestController
public class CategoryController {
	@Autowired
	private ICategoryService categoryService;
	
	@PostMapping("admin/categories")
	public ApiRespond<String> createCategory( @RequestBody Map<String, Object> json){
		String name=(String)json.get("name");
		if(name==null||name.trim().isEmpty()) throw new AppException(ErrorCode.INFOR_EMPTY);
		categoryService.createCategory(name);
		return ApiRespond.<String>builder().build();
	}
	@PutMapping("admin/categories/{id}")
	public ApiRespond<String> updateCategory( @RequestBody Map<String, Object> json, @PathVariable(name = "id") int id){
		String name=(String)json.get("name");
		Boolean isDeleted=(Boolean) json.getOrDefault("isDeleted",null);
		if(name==null||name.trim().isEmpty()) throw new AppException(ErrorCode.INFOR_EMPTY);
		categoryService.updateCategory(name,isDeleted, id);
		return ApiRespond.<String>builder().build();
	}
	@GetMapping("admin/categories")
	public ApiRespond<List<CategoryResponse>> getAll(){
		return ApiRespond.<List<CategoryResponse>>builder()
				.results(categoryService.getAll(null))
				.build();
	}
	@GetMapping("user/categories")
	public ApiRespond<List<CategoryResponse>> getAllNotDeleted(){
		return ApiRespond.<List<CategoryResponse>>builder()
				.results(categoryService.getAll(false))
				.build();
	}
	@PutMapping("admin/categories/toggle-visibility")
	public ApiRespond<String> deleteCategory(@RequestBody Map<String, Object> json){
		List<Integer> list =(ArrayList<Integer>)json.get("categoryIds");
		boolean isDeleted=!(boolean)json.get("visible");
		categoryService.deleteCategory(list,isDeleted);
		
		return ApiRespond.<String>builder()
				.build();
	}
	@GetMapping("admin/categories/gather-category")
	public ApiRespond<List<String>> gatherCategory(@RequestParam int  id){
		
		ApiRespond<List<String>> result=ApiRespond.<List<String>>builder()
				.results(categoryService.gatherCategory(id))
				.build();
		return result;
	}
}
