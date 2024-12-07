package com.example.bookstore.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookstore.dto.ReviewInList;
import com.example.bookstore.entity.Review;
import com.example.bookstore.entity.ReviewPK;
@Repository
public interface ReviewRepository extends JpaRepository<Review, ReviewPK>{
	@Query("select new com.example.bookstore.dto.ReviewInList(r.id.userId,r.id.itemId, r.content,r.rate,r.hidden,"
			+ " i.title, u.fullName,u.urlAvatar,r.createAt,r.updateAt) from Review r join r.item i join r.user u "
			)
	List<ReviewInList> getAll();
	
	@Query("update Review r set r.hidden=?3,r.updateAt=?1 where r.id in ?2")
	@Transactional
	@Modifying
	void hideByListId(Date updateAt,List<ReviewPK> reviewIds,boolean hidden);
}
