package com.example.bookstore.service.Iservice;

import java.util.List;

import com.example.bookstore.dto.ReviewCreateRequest;
import com.example.bookstore.dto.ReviewInList;

public interface IReviewService {
	List<ReviewInList> getAll();
	void hideListReview(List<String> MIds,boolean hidden);
	void createReview(String userId,ReviewCreateRequest reviewCreateRequest,boolean update, String id);
	void deleteReview(String userId,String id);
}
