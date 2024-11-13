package com.example.bookstore.dto;

import java.util.Date;
import java.util.List;

import com.example.bookstore.entity.Category;
import com.example.bookstore.entity.Image;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ItemRequest {
	@NotBlank(message = "INFOR_EMPTY")
	private String author;
	@NotBlank(message = "INFOR_EMPTY")
	private String title;
	@NotBlank(message = "INFOR_EMPTY")
	private String description;
		
	private String coverType;
	
	private String translator;
	@Min(value = 0,message = "INFOR_INVALID")
	private int price;
	@Min(value = 0,message = "INFOR_INVALID")
	private int width;
	@Min(value = 0,message = "INFOR_INVALID")
	private int height;
	@Min(value = 0,message = "INFOR_INVALID")
	private int page;
	
	private Date publishDate;
	
	private int quality;
	
	private int soldCount;

	private List<String> urlImages;

	private List<Integer> categories;
}
