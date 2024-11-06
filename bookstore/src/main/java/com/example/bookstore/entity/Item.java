package com.example.bookstore.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the items database table.
 *
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="item")
public class Item  {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id",unique=true, nullable=false, length=50)
	private String id;

	@Column(name = "author",nullable=false, length=100)
	private String author;

	@Column(name = "title",nullable=false, length=255)
	private String title;

	@OneToMany(mappedBy="item",fetch = FetchType.EAGER)
	private List<Cartitem> cartitems;

	@OneToMany(mappedBy="item",fetch = FetchType.EAGER)
	private List<Image> images;

	@OneToMany(mappedBy="item",fetch = FetchType.EAGER)
	private List<Orderitem> orderitems;

	@OneToMany(mappedBy="item",fetch = FetchType.EAGER)
	private List<Review> reviews;

	@ManyToMany(mappedBy = "items",fetch = FetchType.EAGER)
	private List<Category> categories;

}