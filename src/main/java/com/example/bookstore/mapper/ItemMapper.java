package com.example.bookstore.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


import com.example.bookstore.dto.ItemDetail;
import com.example.bookstore.entity.Item;

@Mapper(componentModel = "spring")
public interface ItemMapper {
	@Mapping(target = "id", ignore = true)
	Item toItem(ItemDetail itemRequest);
	ItemDetail toItemDetail(Item item);
	
	@Mapping(target = "id", ignore = true)
	void updateItem(ItemDetail itemDetail,@MappingTarget Item item);
}
