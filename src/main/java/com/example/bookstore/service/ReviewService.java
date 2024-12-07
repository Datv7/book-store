package com.example.bookstore.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookstore.configuration.AppException;
import com.example.bookstore.configuration.ErrorCode;
import com.example.bookstore.dto.ReviewCreateRequest;
import com.example.bookstore.dto.ReviewInList;
import com.example.bookstore.entity.Review;
import com.example.bookstore.entity.ReviewPK;
import com.example.bookstore.repository.ItemRepository;
import com.example.bookstore.repository.ReviewRepository;
import com.example.bookstore.service.Iservice.IItemService;
import com.example.bookstore.service.Iservice.IReviewService;

@Service
public class ReviewService implements IReviewService{
	@Autowired
	private ReviewRepository reviewRepository;
	@Autowired
	private ItemRepository itemRepository;
	
	@Override
	public List<ReviewInList> getAll() {
		// TODO Auto-generated method stub
		return reviewRepository.getAll();
	}

	@Override
	public void hideListReview(List<String> MIds,boolean hidden) {
		// TODO Auto-generated method stub
		List<ReviewPK> reviewIds=MIds.stream().map(mid-> {
			String[] temp=mid.split(":");
			return new ReviewPK(temp[0], temp[1]);
		}).collect(Collectors.toList());
		reviewRepository.hideByListId(new Date(), reviewIds, hidden);
	}

	@Override
	public void createReview(String userId, ReviewCreateRequest reviewCreateRequest, boolean update, String id) {
		// TODO Auto-generated method stub
		Review review=null;
		
		if(update) {
			String part[]=id.split(":");
			if(userId!=part[0]) throw new AppException(ErrorCode.UNAUTHORIZED);
			review=reviewRepository.findById(new ReviewPK(part[0], part[1])).orElseThrow(()->new AppException(ErrorCode.REVIEW_NOT_EXISTED));

		}else {
			if(!itemRepository.existsById(reviewCreateRequest.getBookId())) throw new AppException(ErrorCode.ITEM_NOT_EXISTED);
			review=new Review();
			review.setId(new ReviewPK(userId, reviewCreateRequest.getBookId()));
		}
		review.setContent(reviewCreateRequest.getContent());
		review.setRate(reviewCreateRequest.getRating());
		reviewRepository.save(review);
	}

	@Override
	public void deleteReview(String userId, String id) {
		// TODO Auto-generated method stub
		String part[]=id.split(":");
		if(userId!=part[0]) throw new AppException(ErrorCode.UNAUTHORIZED);
		boolean existed=reviewRepository.existsById(new ReviewPK(part[0], part[1]));
		if(!existed) throw new AppException(ErrorCode.REVIEW_NOT_EXISTED);
		reviewRepository.deleteById(new ReviewPK(part[0], part[1]));
	}

}
