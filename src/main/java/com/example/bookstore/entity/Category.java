package com.example.bookstore.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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
	
	@Column(name = "createAt")
	private Date createAt;
	
	@Column(name = "updateAt")
	private Date updateAt;
	
	@Builder.Default
	@ManyToMany(mappedBy = "categories")
	private List<Item> items=new ArrayList<Item>();

	@PrePersist
	public void persist() {
		Date date=new Date();
		createAt=date;
		updateAt=date;
	}
	@PreUpdate
	public void update() {
		updateAt=new Date();
	}

}