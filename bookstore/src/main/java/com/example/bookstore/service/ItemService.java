package com.example.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookstore.configuration.AppException;
import com.example.bookstore.configuration.ErrorCode;
import com.example.bookstore.entity.Item;
import com.example.bookstore.repository.ItemRepository;
import com.example.bookstore.service.imp.IItemService;
@Service
public class ItemService implements IItemService{

	@Autowired
	private ItemRepository itemRepository;
	@Override
	public List<Item> findAll() {
		// TODO Auto-generated method stub
		return itemRepository.findAll();
	}

	@Override
	public Item updateItem(String id,Item item) {
		// TODO Auto-generated method stub
		
		Item temp=getItem(id);
		temp.setAuthor(item.getAuthor());
		return itemRepository.save(temp);
	}
	@Override
	public Item creatItem(Item item) {
		// TODO Auto-generated method stub

		return itemRepository.save(item);
	}
	@Override
	public void deleteItem(String id) {
		// TODO Auto-generated method stub
		Item temp=getItem(id);
		itemRepository.delete(temp);
	}
	public Item getItem(String id) {
		return itemRepository.findById(id).orElseThrow(() ->  new AppException(ErrorCode.ITEM_NOT_EXISTED));
	}

}
