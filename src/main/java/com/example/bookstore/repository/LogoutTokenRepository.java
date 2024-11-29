package com.example.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookstore.entity.LogoutToken;
@Repository
public interface LogoutTokenRepository extends JpaRepository<LogoutToken, String>{

}
