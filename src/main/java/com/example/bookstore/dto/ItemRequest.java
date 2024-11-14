package com.example.bookstore.dto;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.example.bookstore.entity.Image;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {
	@NotBlank(message = "INFOR_EMPTY")
	private String author;
	@NotBlank(message = "INFOR_EMPTY")
	private String title;
	@NotBlank(message = "INFOR_EMPTY")
	private String description;
		
	private String coverType;
	
	private String manufacturer;
	
	private String translator;
	@Min(value = 0,message = "INFOR_INVALID")
	private int price;
	@Min(value = 0,message = "INFOR_INVALID")
	private double width;
	@Min(value = 0,message = "INFOR_INVALID")
	private double height;
	@Min(value = 0,message = "INFOR_INVALID")
	private int page;
	
	private Date publishDate;
	
	private int quality;
	
	private int soldCount;
	
	@NotBlank(message = "INFOR_EMPTY")
	private String thumbnailUrl;
	
	private Set<String> gallery;
	
	private List<Integer> categories;

}
