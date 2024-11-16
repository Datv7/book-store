package com.example.bookstore.service.Iservice;

import java.util.List;

import com.example.bookstore.dto.CategoryResponse;

public interface ICategoryService {
	void createCategory(String name);
	void updateCategory(String name,boolean isDeleted, int id);
	void deleteCategory(int id);
	List<CategoryResponse> getAll();
	List<String> gatherCategory(int id);
}
