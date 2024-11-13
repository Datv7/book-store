package com.example.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookstore.dto.ApiRespond;
import com.example.bookstore.entity.Item;
import com.example.bookstore.service.Iservice.IItemService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/items")
public class ItemController {
	@Autowired
	private IItemService iItemService;
	
	@GetMapping("")
	public ApiRespond<List<Item>> findAll(){
		
		ApiRespond<List<Item>> result= ApiRespond.<List<Item>>builder()
				.results(iItemService.findAll())
				.build();
		return result;
	}
	@PostMapping("")
	public ApiRespond creatItem(@RequestBody Item item){
		
		
		return ApiRespond.builder().build();
	}
//	@PutMapping("/{id}")
//	public ApiRespond<Item> updateRole(@PathVariable String id,@RequestBody Item item){
//		
//		ApiRespond<Item> result= ApiRespond.<Item>builder()
//				.results(iItemService.updateItem(id,item))
//				.build();
//		 return result;
//	}
	@DeleteMapping("/{id}")
	public ApiRespond<Object> deleteItem(@PathVariable String id){
		iItemService.deleteItem(id);
		ApiRespond<Object> result= ApiRespond.builder()
				.build();
		return result;
	}
}
