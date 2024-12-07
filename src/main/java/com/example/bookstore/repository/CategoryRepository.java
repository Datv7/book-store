package com.example.bookstore.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookstore.dto.CategoryResponse;
import com.example.bookstore.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{
	@Query("select new com.example.bookstore.dto.CategoryResponse(c.id,c.name,c.isDeleted,c.createAt,c.updateAt) "
			+ "from Category c where (?1 is null or c.isDeleted=?1)")
	List<CategoryResponse> getAll(Boolean deleted);
	
	@Query("select c from Category c where c.name=?1")
	Category findByName(String name);
	
	@Transactional
	@Modifying
	@Query("update Category c set c.isDeleted=?3,c.updateAt=?1 where c.id in ?2")
	void deleteByListId(Date updateAt,List<Integer> categoryIds,boolean isDeleted);
	
//	boolean existsByIdAndIsDeleted(int id,boolean isDeleted);
}
