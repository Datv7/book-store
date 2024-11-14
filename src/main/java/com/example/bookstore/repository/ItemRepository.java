package com.example.bookstore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.bookstore.entity.Item;
import com.example.bookstore.entity.User;
@Repository
public interface ItemRepository extends JpaRepository<Item, String>{
//	@Query("select i from Item i join fetch i.categories where i.id=?1")
//	Optional<Item> getWithCategories(String id);
	boolean existsByTitle(String title);
}
