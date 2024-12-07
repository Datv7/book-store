package com.example.bookstore.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
public class ReviewInList {
	private String id;
	@JsonProperty("description")
	private String content;
	@JsonProperty("rating")
	private int rate;
	@JsonProperty("isHidden")
	private boolean hidden;
	@JsonProperty("bookName")
	private String titleItem;
	
	private String reviewerName;
	private String reviewerAvatar;
	private Date createAt;
	private Date updateAt;
	public ReviewInList(String userId,String itemId, String content, int rate, boolean hidden, String titleItem, String reviewerName,
			String reviewerAvatar, Date createAt, Date updateAt) {

		this.id = userId+":"+itemId;
		this.content = content;
		this.rate = rate;
		this.hidden = hidden;
		this.titleItem = titleItem;
		this.reviewerName = reviewerName;
		this.reviewerAvatar = reviewerAvatar;
		this.createAt = createAt;
		this.updateAt = updateAt;
	}
	
	
	
}
