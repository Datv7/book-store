package com.example.bookstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookstore.dto.AuthenicationRequest;
import com.example.bookstore.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, String>{
	boolean existsByFullNameOrEmailOrSdt(String fullName,String email,String sdt);
	boolean existsByFullName(String fullName);
	boolean existsByEmailOrSdt(String email,String sdt);
	Optional<User> findByEmailOrSdt(String email,String sdt);
	Optional<User> findByFullName(String fullName);
	Optional<User> findByEmail(String email);
}
