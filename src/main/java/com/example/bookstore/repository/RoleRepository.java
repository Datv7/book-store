package com.example.bookstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookstore.entity.Role;
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
	boolean existsByAuthorization(String authorization);
	Optional<Role> findByAuthorization(String authorization);
}
