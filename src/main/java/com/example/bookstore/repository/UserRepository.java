package com.example.bookstore.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookstore.dto.ProfileProjection;
import com.example.bookstore.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, String>{
	@Query("select count(r)>0 from User u join u.roles r where r.authorization=?1")
	boolean existsAsAdmin(String role);
	boolean existsByEmailOrPhoneNumber(String email,String phoneNumber);
	boolean existsByEmail(String email);
	Optional<User> findByEmailOrPhoneNumber(String email,String phoneNumber);
	Optional<User> findByEmail(String email);
	
	@Query("update User u set u.version=case when (u.version* ?3>0) then u.version else ?3 end,u.updateAt=?1 where u.id in ?2")
	@Transactional
	@Modifying
	void deleteByListId(Date updateAt,List<String> userIds,int version);
	
	@Query("select u from User u where u.id=?1")
	ProfileProjection getProfile(String id);
}
