package com.example.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookstore.configuration.AppException;
import com.example.bookstore.configuration.ErrorCode;
import com.example.bookstore.dto.CategoryResponse;
import com.example.bookstore.entity.Category;
import com.example.bookstore.repository.CategoryRepository;
import com.example.bookstore.service.Iservice.ICategoryService;

@Service
public class CategoryService implements ICategoryService{
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public void createCategory(String name) {
		// TODO Auto-generated method stub
		categoryRepository.save(Category.builder().name(name).build());
		
	}

	@Override
	public void updateCategory(String name, int id) {
		// TODO Auto-generated method stub
		Category category=categoryRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
		category.setName(name);
		categoryRepository.save(category);
	}

	@Override
	public List<CategoryResponse> getAll() {
		// TODO Auto-generated method stub
		return categoryRepository.getAll();
	}

}
