package com.example.bookstore.service.Iservice;

import java.util.List;

import com.example.bookstore.dto.ItemRequest;
import com.example.bookstore.dto.RoleResponse;
import com.example.bookstore.entity.Item;

public interface IItemService {
	List<Item> findAll();
	void creatItem(ItemRequest itemRequest,String id,boolean update);
	void deleteItem(String id);
}
