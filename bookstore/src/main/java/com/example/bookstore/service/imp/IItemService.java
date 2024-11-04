package com.example.bookstore.service.imp;

import java.util.List;

import com.example.bookstore.dto.RoleResponse;
import com.example.bookstore.entity.Item;

public interface IItemService {
	List<Item> findAll();
	Item creatItem(Item item);
	Item updateItem(String id,Item item);
	void deleteItem(String id);
}
