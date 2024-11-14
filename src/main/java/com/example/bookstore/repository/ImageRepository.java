package com.example.bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookstore.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, String>{
	@Query("select i from Image i where i.item.id=?1")
	List<Image> findByItemId(String id);
	@Modifying
	@Transactional
	@Query("update Image i set i.isMain=true where i.url=?1 and i.item.id=?2")
	void setMain(String thumbnailUrl,String itemId);
}
