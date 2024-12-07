package com.example.bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.bookstore.dto.AddressProjection;
import com.example.bookstore.entity.Address;

public interface AddressRepository extends JpaRepository<Address, String>{
	@Query("select a from Address a join a.user u where u=?1 and (?2 is null or a.isDeleted=?2)")
	List<AddressProjection> getAll(String userId,Boolean isDeleted);
}
