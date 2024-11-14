package com.example.bookstore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the images database table.
 *
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="image")
public class Image  {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id",unique=true, nullable=false, length=50)
	private String id;

	@Column(name = "url",nullable=false, length=255)
	private String url;
	@Builder.Default
	@Column(name = "isMain",nullable=false)
	private boolean isMain=false;
	
	@ManyToOne
	@JoinColumn(name="item_id")
	private Item item;


}