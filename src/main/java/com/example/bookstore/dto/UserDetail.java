package com.example.bookstore.dto;

import java.text.Normalizer;
import java.util.Date;
import java.util.List;

import com.example.bookstore.entity.Address;
import com.example.bookstore.entity.Order;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
@JsonInclude(value = Include.NON_NULL)
public class UserDetail {
	private String id;
	private String fullName;
	private String email;
	private List<String> roles;
	private String phoneNumber;
	private String urlAvatar;
	private int version;
	private Date createAt;
	private Date updateAt;
	private String slug;
	@Builder.Default
	private List<Order> orders=null;
	
	@Builder.Default
	private List<Address> addresses=null;
	
	public static String genSlug(String name) {
		String normalizedString=name.replaceAll("Đ|đ", "d");
		normalizedString=normalizedString.replaceAll("- ", "");
		normalizedString=normalizedString.trim();
        normalizedString= Normalizer.normalize(normalizedString.toLowerCase(), Normalizer.Form.NFD);
        normalizedString = normalizedString.replaceAll("[^\\p{ASCII}]", "");
        
        String slug = normalizedString.replaceAll("\\s+", "-");

        slug = slug.replaceAll("[^a-z0-9-]", "");

        return slug;
	}
	
}
