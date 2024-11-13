package com.example.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookstore.configuration.AppException;
import com.example.bookstore.configuration.ErrorCode;
import com.example.bookstore.dto.ItemRequest;
import com.example.bookstore.entity.Category;
import com.example.bookstore.entity.Image;
import com.example.bookstore.entity.Item;
import com.example.bookstore.mapper.ItemMapper;
import com.example.bookstore.repository.ImageRepository;
import com.example.bookstore.repository.ItemRepository;
import com.example.bookstore.service.Iservice.IItemService;
@Service
public class ItemService implements IItemService{

	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ImageRepository imageRepository;
	
	
	@Override
	public List<Item> findAll() {
		// TODO Auto-generated method stub
		return itemRepository.findAll();
	}

	@Transactional
	@Override
	public void creatItem(ItemRequest itemRequest,String id, boolean update) {
		// TODO Auto-generated method stub
		Item item=itemMapper.toItem(itemRequest);
		if(update) item.setId(id);
		item=itemRepository.save(item);
		setImage(itemRequest.getUrlImages(), item.getId(), false);
		setCatagory(itemRequest.getCategories(), item.getId());
	}
	@Override
	public void deleteItem(String id) {
		// TODO Auto-generated method stub
		Item temp=getItem(id);
		temp.setQuality(-1);
		itemRepository.save(temp);
	}
	public Item getItem(String id) {
		return itemRepository.findById(id).orElseThrow(() ->  new AppException(ErrorCode.ITEM_NOT_EXISTED));
	}
	public void setImage(List<String> urlImages, String userId,boolean update){
		if(update) {
			imageRepository.findByUserId(userId).forEach(i->{
				if(urlImages.stream().noneMatch(t->t.equals(i.getUrl()))) {
					i.setItem(null);
					imageRepository.save(i);
				}
				else urlImages.remove(i.getUrl());
				
			});;
		}
		urlImages.forEach(t1->{
			imageRepository.save(Image.builder()
					.url(t1)
					.item(Item.builder().id(userId).build())
					.build());
		});
		
		
	}
	@Transactional
	public void setCatagory(List<Integer> categories, String id) {

		Item item=getItem(id);
		categories.forEach(c->{
			item.getCategories().add(Category.builder().id(c).build());
			
		});
		itemRepository.save(item);
		
		
	}

}
