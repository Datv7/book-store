package com.example.bookstore.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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

	@Column(name = "description",nullable=false, length=600)
	private String description;
	
	@Column(name = "coverType", length=50)
	private String coverType;
	
	@Column(name = "translator", length=100)
	private String translator;
	
	@Column(name = "price",nullable=false)
	private int price;
	
	@Column(name = "width",nullable=false)
	private int width;
	
	@Column(name = "height",nullable=false)
	private int height;
	
	@Column(name = "page",nullable=false)
	private int page;
	
	@Column(name = "publishDate",nullable=false)
	private Date publishDate;
	
	@Column(name = "quality",nullable=false)
	private int quality;
	
	@Column(name = "soldCount",nullable=false)
	private int soldCount;
	@Builder.Default
	@OneToMany(mappedBy="item",fetch = FetchType.LAZY)
	private List<Cartitem> cartitems=new ArrayList<Cartitem>();

	@Builder.Default
	@OneToMany(mappedBy="item",fetch = FetchType.LAZY)
	private List<Image> images=new ArrayList<Image>();

	@Builder.Default
	@OneToMany(mappedBy="item",fetch = FetchType.LAZY)
	private List<Orderitem> orderitems=new ArrayList<Orderitem>();

	@Builder.Default
	@OneToMany(mappedBy="item",fetch = FetchType.LAZY)
	private List<Review> reviews=new ArrayList<Review>();

	@Builder.Default
	@ManyToMany
	@JoinTable(
	        name = "categoryItem",
	        joinColumns = @JoinColumn(name = "item_id"),
	        inverseJoinColumns = @JoinColumn(name = "category_id"),
	        uniqueConstraints = @UniqueConstraint(columnNames = {"item_id", "category_id"})
	    )
	private List<Category> categories=new ArrayList<Category>();

}