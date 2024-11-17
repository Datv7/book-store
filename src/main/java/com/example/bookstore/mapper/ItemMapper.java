package com.example.bookstore.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.bookstore.dto.ItemInList;
import com.example.bookstore.dto.ItemRequest;
import com.example.bookstore.entity.Item;

@Mapper(componentModel = "spring")
public interface ItemMapper {
	@Mapping(target = "categories", ignore = true)
	Item toItem(ItemRequest itemRequest);
	ItemInList toItemInList(Item item);
}
