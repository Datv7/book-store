package com.example.bookstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.bookstore.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, String>{
	@Query("select count(r)>0 from User u join u.roles r where r.authorization=?1")
	boolean existsAsAdmin(String role);
	boolean existsByEmailOrPhoneNumber(String email,String phoneNumber);
	Optional<User> findByEmailOrPhoneNumber(String email,String phoneNumber);
	Optional<User> findByEmail(String email);
}
