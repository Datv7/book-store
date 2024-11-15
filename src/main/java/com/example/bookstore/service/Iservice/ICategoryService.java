package com.example.bookstore.service.Iservice;

import java.util.List;

import com.example.bookstore.dto.CategoryResponse;

public interface ICategoryService {
	void createCategory(String name);
	void updateCategory(String name, int id);
	List<CategoryResponse> getAll();
	List<String> gatherCategory(int id);
}
