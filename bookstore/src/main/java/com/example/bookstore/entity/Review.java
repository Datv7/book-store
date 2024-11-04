package com.example.bookstore.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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
@Table(name="reviews")
public class Review  {

	@EmbeddedId
	private ReviewPK id;

	@Column(name = "content",nullable=false,length = 500)
	private String content;

	@Column(name = "rate",nullable=false)
	private int rate;

	@MapsId("itemsId")
	@ManyToOne
	@JoinColumn(name="items_id", nullable=false)
	private Item item;

	@MapsId("usersId")
	@ManyToOne
	@JoinColumn(name="users_id", nullable=false)
	private User user;


}