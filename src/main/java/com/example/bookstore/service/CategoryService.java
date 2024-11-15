package com.example.bookstore.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.bookstore.configuration.AppException;
import com.example.bookstore.configuration.ErrorCode;
import com.example.bookstore.dto.CategoryResponse;
import com.example.bookstore.entity.Category;
import com.example.bookstore.repository.CategoryRepository;
import com.example.bookstore.service.Iservice.ICategoryService;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class CategoryService implements ICategoryService{
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public void createCategory(String name) {
		// TODO Auto-generated method stub
		categoryRepository.save(Category.builder().name(name).build());
		
	}

	@Override
	public void updateCategory(String name, int id) {
		// TODO Auto-generated method stub
		Category category=categoryRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
		category.setName(name);
		categoryRepository.save(category);
	}

	@Override
	public List<CategoryResponse> getAll() {
		// TODO Auto-generated method stub
		return categoryRepository.getAll();
	}

	@Override
	public List<String> gatherCategory(int id) {
		// TODO Auto-generated method stub
		List<String> result=new ArrayList<String>();
		String url="https://tiki.vn/api/v2/categories";
		UriComponentsBuilder uribuild=UriComponentsBuilder.fromHttpUrl(url);
		uribuild.queryParam("parent_id", id);
		uribuild.queryParam("include", "children");
		
		HttpHeaders headers=new HttpHeaders();
		HttpEntity<Void> httpEntity=new HttpEntity<>(headers);
		ResponseEntity<JsonNode> responseEntity= restTemplate.exchange(uribuild.toUriString(), HttpMethod.GET, httpEntity, JsonNode.class);
		
		if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
			System.out.println("succeed");
            JsonNode json=responseEntity.getBody();
            if(!json.get("data").isArray()) throw new AppException(ErrorCode.SERVICE_UNAVAILABLE);
            for(JsonNode data:json.get("data")) {       
            		if(data.get("children")==null) {
            			String name=data.get("name").asText();
                    	System.out.println(name);
                    	try {
                    		createCategory(name);
                    		result.add(name);
                    	}catch(DataIntegrityViolationException e) {
                    		System.out.println(e.getMessage());
                    	}
            		}
            		else {
            			childrenCategory(data.get("children"), result);
            		}
            	
            	
            }
		}else {
			System.out.println("Request failed with status: " + responseEntity.getStatusCode());
            throw new AppException(ErrorCode.SERVICE_UNAVAILABLE);
		}
		return result;
		
	}
	private void childrenCategory(JsonNode node,List<String> result) {
		for(JsonNode json:node) {
			String name=json.get("name").asText();
			try {	
        		createCategory(name);
        		result.add(name);
        	}catch(DataIntegrityViolationException e) {
        		System.out.println(e.getMessage());
        	}
		}
		
	}

}
