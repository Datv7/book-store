package com.example.bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.bookstore.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, String>{
	@Query("select i from Image i where i.item.id=?1")
	List<Image> findByUserId(String id);
}
