package com.example.bookstore.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the reviews database table.
 *
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="review")
public class Review  {

	@EmbeddedId
	private ReviewPK id=new ReviewPK();

	@Column(name = "content",nullable=false,length = 500)
	private String content;

	@Column(name = "rate",nullable=false)
	private int rate;
	
	@Builder.Default
	@Column(name = "hidden",nullable = false)
	private boolean hidden=false;
	
	@Column(name = "createAt")
	private Date createAt;
	
	@Column(name = "updateAt")
	private Date updateAt;

	@MapsId("itemId")
	@ManyToOne
	@JoinColumn(name="item_id",referencedColumnName = "id")
	private Item item;

	@MapsId("userId")
	@ManyToOne
	@JoinColumn(name="user_id",referencedColumnName = "id")
	private User user;

	@PrePersist
	public void persist() {
		Date date=new Date();
		if(createAt==null) {
			createAt=date;
		}
		updateAt=createAt;
	}
	@PreUpdate
	public void update() {
		updateAt=new Date();
	}

}