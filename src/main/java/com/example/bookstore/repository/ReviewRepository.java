package com.example.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookstore.entity.Review;
import com.example.bookstore.entity.ReviewPK;

public interface ReviewRepository extends JpaRepository<Review, ReviewPK>{

}
