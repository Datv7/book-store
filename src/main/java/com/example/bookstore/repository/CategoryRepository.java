package com.example.bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.bookstore.dto.CategoryResponse;
import com.example.bookstore.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{
	@Query("select c from Category c")
	List<CategoryResponse> getAll();
//	@Query("select c from Category c where c.name in ?1")
//	List<CategoryResponse> findAllByName(List<String> names);
	@Query("select c from Category c where c.name=?1")
	Category findByName(String name);
}
