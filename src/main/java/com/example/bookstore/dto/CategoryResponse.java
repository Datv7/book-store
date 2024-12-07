package com.example.bookstore.dto;

import java.text.Normalizer;
import java.util.Date;

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
	@JsonProperty("isHidden")
	private boolean isDeleted;
	
	@JsonProperty("createdAt")
	private Date createAt;
	@JsonProperty("updatedAt")
	private Date updateAt;
	
	
	public static String genSlug(String name) {
		String normalizedString=name.replaceAll("Đ|đ", "d");
		normalizedString=normalizedString.replaceAll("- ", "");
        normalizedString= Normalizer.normalize(normalizedString.toLowerCase(), Normalizer.Form.NFD);
        normalizedString = normalizedString.replaceAll("[^\\p{ASCII}]", "");
        
        String slug = normalizedString.replaceAll("\\s+", "-");

        slug = slug.replaceAll("[^a-z0-9-]", "");

        return slug;
	}
//	public static CategoryResponse mapCategoryResponse(Category c) {
//		return CategoryResponse.builder()
//				.id(c.getId())
//				.name(c.getName())
//				.isDeleted(c.isDeleted())
//				.slug(genSlug(c.getName()))
//				.createAt(c.getCreateAt())
//				.updateAt(c.getUpdateAt())
//				.build();
//	}
	public CategoryResponse(int id, String name, boolean isDeleted, Date createAt, Date updateAt) {
		this.id = id;
		this.name = name;
		this.isDeleted = isDeleted;
		this.createAt = createAt;
		this.updateAt = updateAt;
		this.slug=genSlug(name);
	}
	
}
