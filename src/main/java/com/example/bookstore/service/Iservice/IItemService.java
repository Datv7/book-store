package com.example.bookstore.service.Iservice;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.example.bookstore.dto.ItemInList;
import com.example.bookstore.dto.ItemRequest;
import com.example.bookstore.dto.PageCustom;
import com.example.bookstore.entity.Item;

public interface IItemService {
	PageCustom<ItemInList> getAll(int page, int size);
	Item creatItem(ItemRequest itemRequest,String itemId,boolean update);
	void deleteItem(String id);
	void setImage(String thumbnailUrl,Set<String> gallery, String itemId,boolean update);
	void setCatagory(List<Integer> categories, String id);
	List<String> gatherItem(String  urlKey,String category ,int page,int limit,boolean gatherReview);
}
