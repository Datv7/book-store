package com.example.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookstore.dto.ApiRespond;
import com.example.bookstore.dto.ItemRequest;
import com.example.bookstore.entity.Item;
import com.example.bookstore.service.Iservice.IItemService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/items")
public class ItemController {
	@Autowired
	private IItemService iItemService;
	
	@GetMapping("")
	public ApiRespond<Page<Item>> getByPage(
			@RequestParam(name = "page",defaultValue = "1") int page,
			@RequestParam(name = "limit",defaultValue = "10") int limit){
		
		ApiRespond<Page<Item>> result= ApiRespond.<Page<Item>>builder()
				.results(iItemService.getByPage(page-1, limit))
				.build();
		return result;
	}
	@PostMapping("")
	public ApiRespond creatItem(@Valid @RequestBody ItemRequest itemRequest){
		iItemService.creatItem(itemRequest, null, false);
		return ApiRespond.builder().build();
	}

	@PutMapping("/{id}")
	public ApiRespond updateItem(@Valid @RequestBody ItemRequest itemRequest,@PathVariable(name = "id") String id){
		iItemService.creatItem(itemRequest, id, true);
		System.out.println(itemRequest.getWidth());
		log.warn(String.valueOf(itemRequest.getWidth()));
		return ApiRespond.builder().build();
	}
	
	@DeleteMapping("/{id}")
	public ApiRespond<String> deleteItem(@PathVariable String id){
		iItemService.deleteItem(id);
		ApiRespond<String> result= ApiRespond.<String>builder()
				.build();
		return result;
	}
	@GetMapping("/gather-item")
	public ApiRespond<List<String>> gatherItem(
			@RequestParam String  urlKey,
			@RequestParam String category , 
			@RequestParam(defaultValue = "1") int page, 
			@RequestParam(defaultValue = "20") int limit){
		
		ApiRespond<List<String>> result=ApiRespond.<List<String>>builder().results(iItemService.gatherItem(urlKey, category, page, limit)).build();
		return result;
	}
}
