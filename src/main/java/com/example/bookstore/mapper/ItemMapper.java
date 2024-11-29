package com.example.bookstore.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.bookstore.dto.ItemRequest;
import com.example.bookstore.entity.Item;

@Mapper(componentModel = "spring")
public interface ItemMapper {
	@Mapping(target = "categories", ignore = true)
	@Mapping(target = "images", ignore = true)
	Item toItem(ItemRequest itemRequest);
}
