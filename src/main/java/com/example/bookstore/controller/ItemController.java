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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookstore.dto.ApiRespond;
import com.example.bookstore.dto.ItemDetail;
import com.example.bookstore.dto.PageCustom;
import com.example.bookstore.service.Iservice.IItemService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
@RestController
//@RequestMapping("/items")
public class ItemController {
	@Autowired
	private IItemService iItemService;
	
	@GetMapping("/admin/books")
	public ApiRespond<List<ItemDetail>> getAll(){
		
		ApiRespond<List<ItemDetail>> result= ApiRespond.<List<ItemDetail>>builder()
				.results(iItemService.getAll())
				.build();
		return result;
	}
	@PostMapping("/admin/books")
	public ApiRespond creatItem(@Valid @RequestBody ItemDetail itemRequest){
		iItemService.creatItem(itemRequest, null, false);
		return ApiRespond.builder().build();
	}

	@PutMapping("/admin/books/{id}")
	public ApiRespond updateItem(@Valid @RequestBody ItemDetail itemRequest,@PathVariable(name = "id") String id){
		iItemService.creatItem(itemRequest, id, true);
		System.out.println(itemRequest.getWidth());
		log.warn(String.valueOf(itemRequest.getWidth()));
		return ApiRespond.builder().build();
	}
	
	@DeleteMapping("/admin/books")
	public ApiRespond<String> deleteItem(@RequestBody Map<String, Object> json){
		List<String> list =(ArrayList<String>)json.get("categoryIds");
		iItemService.deleteItem(list);
		ApiRespond<String> result= ApiRespond.<String>builder()
				.build();
		return result;
	}
	@GetMapping("/gather-item")
	public ApiRespond<List<String>> gatherItem(
			@RequestParam String  urlKey,
			@RequestParam String category , 
			@RequestParam(defaultValue = "1") int page, 
			@RequestParam(defaultValue = "20") int limit,
			@RequestParam(name = "categories",required = false) List<String> targetCategory,
			@RequestParam(name = "gatherReview",required = false,defaultValue = "false") boolean gatherReview
			){
		
		ApiRespond<List<String>> result=ApiRespond.<List<String>>builder().results(iItemService.gatherItem(urlKey, category, page, limit,gatherReview)).build();
		return result;
	}
}
