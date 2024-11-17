package com.example.bookstore.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ItemInList {
	
	private String id;

	private String author;

	private String title;
	
	private String description;
	
	private String coverType;
	
	private String translator;
	
	private String manufacturer;
	
	private int price;
		
	private double width;
	
	private double height;
	
	private int page;
	
	private int quantity;
	
	private int soldCount;
}
