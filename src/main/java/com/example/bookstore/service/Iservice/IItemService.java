package com.example.bookstore.service.Iservice;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.example.bookstore.dto.ItemDetail;
import com.example.bookstore.dto.PageCustom;
import com.example.bookstore.entity.Item;

public interface IItemService {
	List<ItemDetail> getAll();
	Item creatItem(ItemDetail itemRequest,String itemId,boolean update);
	void deleteItem(List<String> bookIds);
	void setImage(String thumbnailUrl,Set<String> gallery, String itemId,boolean update);
	void setCatagory(List<Integer> categories, String id);
	List<String> gatherItem(String  urlKey,String category ,int page,int limit,boolean gatherReview);
}
