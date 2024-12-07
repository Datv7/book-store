package com.example.bookstore.dto;

import java.text.Normalizer;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.example.bookstore.entity.Category;
import com.example.bookstore.entity.Image;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class ItemDetail {
	
	private String id;
	
	@JsonProperty("authors")
	@NotBlank(message = "INFOR_EMPTY")
	private String author;
	
	@JsonProperty("name")
	@NotBlank(message = "INFOR_EMPTY")
	private String title;
	@NotBlank(message = "INFOR_EMPTY")
	private String description;
		
	private String coverType;
	
	@JsonProperty("publisher")
	private String manufacturer;
	
	private String translator;
	@Min(value = 0,message = "INFOR_INVALID")
	private int price;
	@Min(value = 0,message = "INFOR_INVALID")
	private int originalPrice;
	@Min(value = 0,message = "INFOR_INVALID")
	private double width;
	@Min(value = 0,message = "INFOR_INVALID")
	private double height;
	
	@JsonProperty("totalPages")
	@Min(value = 0,message = "INFOR_INVALID")
	private int page;
	
	private Date publishDate;
	
	@JsonProperty("stock")
	private int quality;
	
	private int soldCount;
	
	private Date createAt;
	private Date updateAt;
	
	@JsonProperty("thumbnail")
	@NotBlank(message = "INFOR_EMPTY")
	private String thumbnailUrl;
	
	private String slug;
	
	@JsonProperty("images")
	private Set<String> gallery;
	@JsonProperty("categoryId")
	private Integer category;
	
	public static String genSlug(String name) {
		String normalizedString=name.replaceAll("Đ|đ", "d");
		normalizedString=normalizedString.replaceAll("- ", "");
        normalizedString= Normalizer.normalize(normalizedString.toLowerCase(), Normalizer.Form.NFD);
        normalizedString = normalizedString.replaceAll("[^\\p{ASCII}]", "");
        
        String slug = normalizedString.replaceAll("\\s+", "-");

        slug = slug.replaceAll("[^a-z0-9-]", "");

        return slug;
	}
	public void loadSlug() {
		slug=genSlug(title);
	}

}
