package com.example.bookstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookstore.dto.AuthenicationRequest;
import com.example.bookstore.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, String>{
	boolean existsByFullNameOrEmail(String fullName,String email);
	boolean existsByFullName(String fullName);

	Optional<User> findByFullName(String fullName);
}
