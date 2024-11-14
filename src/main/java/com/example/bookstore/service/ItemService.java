package com.example.bookstore.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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
import com.fasterxml.jackson.databind.JsonNode;
@Service
public class ItemService implements IItemService{

	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ImageRepository imageRepository;
	@Autowired
	private RestTemplate restTemplate;
	
	
	@Override
	public Page<Item> getByPage(int page , int limit) {
		// TODO Auto-generated method stub
		Pageable pageable=PageRequest.of(page, limit);
		return itemRepository.findAll(pageable);
	}

	@Transactional
	@Override
	public void creatItem(ItemRequest itemRequest,String itemId, boolean update) {
		// TODO Auto-generated method stub
		Item item=itemMapper.toItem(itemRequest);
		if(update) item.setId(itemId);
		item=itemRepository.save(item);
		setImage(itemRequest.getThumbnailUrl(),itemRequest.getGallery(), item.getId(), update);
		setCatagory(itemRequest.getCategories(), item.getId());
	}
	
	
	@Override
	public void deleteItem(String id) {
		// TODO Auto-generated method stub
		Item temp=getItem(id);
		temp.setQuantity(-1);
		itemRepository.save(temp);
	}
	public Item getItem(String id) {
		return itemRepository.findById(id).orElseThrow(() ->  new AppException(ErrorCode.ITEM_NOT_EXISTED));
	}
	@Override
	public void setImage(String thumbnailUrl,Set<String> gallery, String itemId,boolean update){
		gallery.add(thumbnailUrl);
		if(update) {
			for(Image i: imageRepository.findByItemId(itemId)) {
				if(i.isMain()) {
					i.setMain(false);
				}
				if(!gallery.contains(i.getUrl())) {
					i.setItem(null);
					imageRepository.save(i);
				} else {
					gallery.remove(i.getUrl());
				}
				
			};
		}
		gallery.forEach(t1->{
			imageRepository.save(Image.builder()
					.url(t1)
					.item(Item.builder().id(itemId).build())
					.build());
		});
		imageRepository.setMain(thumbnailUrl, itemId);
		
	}
	@Transactional
	@Override
	public void setCatagory(List<Integer> categories, String id) {
		if(categories==null||categories.isEmpty()) return;
		Item item=getItem(id);
		item.getCategories().size();
		item.getCategories().clear();
		categories.forEach(c->{
			item.getCategories().add(Category.builder().id(c).build());
			
		});
		itemRepository.save(item);
		
		
	}


	@Override
	public List<String> gatherItem(String urlKey, String category, int page, int limit) {
		// TODO Auto-generated method stub
		List<String> result=new ArrayList<String>();
		
		String url="https://tiki.vn/api/personalish/v1/blocks/listings";
		UriComponentsBuilder uribuild=UriComponentsBuilder.fromHttpUrl(url);
		uribuild.queryParam("urlKey", urlKey);
		uribuild.queryParam("page", page!=0?page:1);
		uribuild.queryParam("category", category.substring(1));
		uribuild.queryParam("limit", limit !=0 ? limit:40);
		
		HttpHeaders headers=new HttpHeaders();
		HttpEntity<Void> httpEntity=new HttpEntity<>(headers);
		ResponseEntity<JsonNode> responseEntity= restTemplate.exchange(uribuild.toUriString(), HttpMethod.GET, httpEntity, JsonNode.class);
		
		if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            System.out.println("succeed");
            JsonNode json=responseEntity.getBody();
            if(!json.get("data").isArray()) throw new AppException(ErrorCode.SERVICE_UNAVAILABLE);
            int i=1;
            for(JsonNode data:json.get("data")) {
            	System.out.println(i+"--"+data.get("name").asText());
            	i++;
            	System.out.println("-----------");
            	String id=data.get("id").asText();
            	if(id==null) {
            		System.out.println("item null");
            		continue;
            	}
            	String[] url_path=data.get("url_path").asText().split("spid=");
            	String url2="https://tiki.vn/api/v2/products/" +id;
        		UriComponentsBuilder uribuild2=UriComponentsBuilder.fromHttpUrl(url2);
        		uribuild2.queryParam("spid", url_path[1]);
        		HttpHeaders headers2=new HttpHeaders();
        		HttpEntity<Void> httpEntity2=new HttpEntity<Void>(headers2);
        		ResponseEntity<JsonNode> responseEntity2= restTemplate.exchange(uribuild2.toUriString(), HttpMethod.GET, httpEntity2, JsonNode.class);
        		if (responseEntity2.getStatusCode().equals(HttpStatus.OK)) {
        			ItemRequest itemRequest=new ItemRequest();
                    System.out.println("succeed");
                    JsonNode json2=responseEntity2.getBody();
                    
        			String title=json2.get("name").asText();
        			itemRequest.setTitle(title);
        			
        			if(itemRepository.existsByTitle(title)) continue;
        			
        			int price=Integer.parseInt( json2.get("original_price").asText());
        			itemRequest.setPrice(price);
        			
        			String tempDescription=json2.get("description").asText();
        			int y=tempDescription.lastIndexOf("<p>");
        			String description=tempDescription.substring(0, y);
        			itemRequest.setDescription(description);
        			
        			Set<String> gallery= new HashSet<String>();
        			for(JsonNode image: json2.get("images")) {
        				gallery.add( image.get("base_url").asText());
        			}
        			
        			itemRequest.setGallery(gallery);
        			String thumbnailUrl=json2.get("thumbnail_url").asText().replaceFirst("/cache/[^/]+/", "/");
        			itemRequest.setThumbnailUrl(thumbnailUrl);
        			System.out.println(thumbnailUrl);
        			String authors="";
        			int ii=0;
        			for(JsonNode author: json2.get("authors")) {
        				if(ii>0) authors=authors + ", ";
        				authors=authors+author.get("name").asText();
        				ii++;
        			}
        			itemRequest.setAuthor(authors);
        			
        			String coverType=null;
        			String translator=null;
        			String manufacturer=null;
        			double width=0;
        			double height=0;
        			int page1=0;
        			
        			Date publishDate=null;
        			for(JsonNode specification: json2.get("specifications")) {
        				for(JsonNode attribute: specification.get("attributes")) {
        					String attributeCode=attribute.get("code").asText();
        					String value=attribute.get("value").asText();
        					if(attributeCode.equals("publication_date")) {
        						String date = value;
        				        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        				        try {
        				            publishDate = formatter.parse(date);
        				        } catch (ParseException e) {
        				            System.out.println("Error parsing date: " + e.getMessage());
        				        }
        				        itemRequest.setPublishDate(publishDate);        					
        				    }
        					else if(attributeCode.equals("dimensions")) {
        						String dimension=(value.replaceAll("<p>|</p>|cm", "")).replaceAll(",", ".");
        						String [] parts=dimension.split("x");
        						try {
        							width=Double.parseDouble(parts[0].trim());
            						height=Double.parseDouble(parts[1].trim());
								} catch (NumberFormatException e) {
									// TODO: handle exception
									continue;
								}
        						
        						itemRequest.setWidth(width);
        						itemRequest.setHeight(height);
        					}
        					else if(attributeCode.equals("dich_gia")) {
        						translator=value;
        						itemRequest.setTranslator(translator);
        					}
        					else if(attributeCode.equals("book_cover")) {
        						coverType=value;
        						itemRequest.setCoverType(coverType);
        					}
        					else if(attributeCode.equals("number_of_page")) {
        						page1=Integer.parseInt( value);
        						itemRequest.setPage(page1);        						
        					}
        					else if( attributeCode.equals("manufacturer")){
        						manufacturer=value;
        						itemRequest.setManufacturer(manufacturer);        						
        					}
        					
            			}
        			}
        			
        			int soldCount=Integer.parseInt( json2.get("quantity_sold").get("value").asText());
        			itemRequest.setSoldCount(soldCount);
        			try {
        				creatItem(itemRequest, null, false);
        				result.add(itemRequest.getTitle());   
        			}catch (DataIntegrityViolationException e) {
        				System.out.println(e.getMessage());
        				
        			}
        			
        			     			
        			
        		}else {
                    System.out.println(" Get item failed" + responseEntity2.getStatusCode());
                }
            	System.out.println("-----------");
            }
            
            
        } else {
        	
            System.out.println("Request failed with status: " + responseEntity.getStatusCode());
            throw new AppException(ErrorCode.SERVICE_UNAVAILABLE);
        }
		return result;
	}

}
