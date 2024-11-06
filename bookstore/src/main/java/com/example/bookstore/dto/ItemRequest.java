package com.example.bookstore.dto;

import java.util.Date;
import java.util.List;

import com.example.bookstore.entity.Cartitem;
import com.example.bookstore.entity.Category;
import com.example.bookstore.entity.Image;
import com.example.bookstore.entity.Orderitem;
import com.example.bookstore.entity.Review;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.UniqueConstraint;

public class ItemRequest {
	private String id;
	private String author;

	private String title;

	private String description;
	
	private String coverType;
	
	private String translator;
	
	private int price;
	
	private int width;
	
	private int height;
	
	private int page;
	
	private Date publishDate;
	
	private int quality;
	
	private int soldCount;

	private List<Image> images;

	private List<Category> categories;
}
