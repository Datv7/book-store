package com.example.bookstore.service.Iservice;

import java.util.List;

import com.example.bookstore.dto.CategoryResponse;

public interface ICategoryService {
	void createCategory(String name);
	void updateCategory(String name,Boolean isDeleted, int id);
	void deleteCategory(List<Integer> ids,boolean isDeleted);
	List<CategoryResponse> getAll(Boolean deleted);
	List<String> gatherCategory(int id);
}
