package com.example.bookstore.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookstore.dto.ApiRespond;
import com.example.bookstore.dto.ReviewCreateRequest;
import com.example.bookstore.dto.ReviewInList;
import com.example.bookstore.service.Iservice.IReviewService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
@RestController
public class ReviewController {
	@Autowired
	private IReviewService iReviewService;
	
	@GetMapping("/admin/reviews")
	public ApiRespond<List<ReviewInList>> getAll(){
		
		return ApiRespond.<List<ReviewInList>>builder().results(iReviewService.getAll()).build();
	}
	
	@PutMapping("/admin/reviews/toggle-visibility")
	public ApiRespond<String> hideListReview(@RequestBody Map<String, Object> json){
		List<String> list =(ArrayList<String>)json.get("reviewIds");
		boolean hidden=!(boolean)json.get("visible");
		iReviewService.hideListReview(list,hidden);
		return ApiRespond.<String>builder().build();
	}
	
	@PostMapping("user/reviews")
	public ApiRespond<String> createReview(Authentication authentication,@Valid @RequestBody ReviewCreateRequest reviewCreateRequest){
		String userId=authentication.getName();
		iReviewService.createReview(userId, reviewCreateRequest, false, null);
		return ApiRespond.<String>builder().build();
	}
	@PutMapping("user/reviews/{id}")
	public ApiRespond<String> updateReview(Authentication authentication,
			@Valid @RequestBody ReviewCreateRequest reviewCreateRequest,
			@PathVariable("id") String id){
		String userId=authentication.getName();
		iReviewService.createReview(userId, reviewCreateRequest, true, id);
		return ApiRespond.<String>builder().build();
	}
	@DeleteMapping("user/reviews/{id}")
	public ApiRespond<String> deleteReview(Authentication authentication,@PathVariable("id") String id){
		String userId=authentication.getName();
		iReviewService.deleteReview(userId, id);
		return ApiRespond.<String>builder().build();
	}
}
