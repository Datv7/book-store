package com.example.bookstore.dto;

import java.text.Normalizer;

import com.example.bookstore.entity.Category;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
	private int id;
	private String name;
	private String slug;
	@JsonProperty("isDeleted")
	private boolean isDeleted;
	
	
	
	public static String genSlug(String name) {
		String normalizedString=name.replaceAll("Đ|đ", "d");
		normalizedString=normalizedString.replaceAll("- ", "");
        normalizedString= Normalizer.normalize(normalizedString.toLowerCase(), Normalizer.Form.NFD);
        normalizedString = normalizedString.replaceAll("[^\\p{ASCII}]", "");
        
        String slug = normalizedString.replaceAll("\\s+", "-");

        slug = slug.replaceAll("[^a-z0-9-]", "");

        return slug;
	}
	public static CategoryResponse mapCategoryResponse(Category c) {
		return new CategoryResponse(c.getId(), c.getName(), genSlug(c.getName()), c.isDeleted());
	}
	
}
