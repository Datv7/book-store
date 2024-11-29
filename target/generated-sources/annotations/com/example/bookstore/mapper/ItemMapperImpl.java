package com.example.bookstore.mapper;

import com.example.bookstore.dto.ItemRequest;
import com.example.bookstore.entity.Item;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class ItemMapperImpl implements ItemMapper {

    @Override
    public Item toItem(ItemRequest itemRequest) {
        if ( itemRequest == null ) {
            return null;
        }

        Item.ItemBuilder item = Item.builder();

        item.author( itemRequest.getAuthor() );
        item.title( itemRequest.getTitle() );
        item.description( itemRequest.getDescription() );
        item.coverType( itemRequest.getCoverType() );
        item.translator( itemRequest.getTranslator() );
        item.price( itemRequest.getPrice() );
        item.width( itemRequest.getWidth() );
        item.height( itemRequest.getHeight() );
        item.page( itemRequest.getPage() );
        item.publishDate( itemRequest.getPublishDate() );
        item.quality( itemRequest.getQuality() );
        item.soldCount( itemRequest.getSoldCount() );

        return item.build();
    }
}
