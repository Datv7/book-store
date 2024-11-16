package com.example.bookstore.entity;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the categories database table.
 *
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="category")
public class Category  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id",unique=true, nullable=false)
	private Integer id;

	@Column(name = "name",nullable=false, length=100)
	private String name;
	
	@Builder.Default
	@Column(name = "isDeleted",nullable = false)
	private boolean isDeleted=false;
	
	@Builder.Default
	@ManyToMany(mappedBy = "categories")
	private List<Item> items=new ArrayList<Item>();

	

}