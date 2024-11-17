package com.example.bookstore.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PageCustom <T>{
	private int totalPage;
	private List<T> data;
}
