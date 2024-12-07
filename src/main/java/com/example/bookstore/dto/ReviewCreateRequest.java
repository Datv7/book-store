package com.example.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
@Getter
public class ReviewCreateRequest {
	private String bookId;
	@JsonProperty("description")
	@NotBlank(message = "INFOR_EMPTY")
	private String content;
	@Min(value = 0,message = "INFOR_INVALID")
	private int rating;
}
