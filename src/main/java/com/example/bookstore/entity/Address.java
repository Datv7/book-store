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

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="address") 
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id",unique=true, nullable=false, length=50)
	private String id;
	
	@Column(name = "sdt",nullable=false, length=20)
	private String sdt;
	@Column(name = "detail",nullable=false, length=200)
	private String detail;
	
	@Column(name = "district",nullable=false)
	private String district;
	
	@Column(name = "province",nullable=false)
	private String province;
	
	@Column(name = "ward",nullable=false)
	private String ward;
	
	@ManyToOne
	@JoinColumn(name = "userId",nullable=false)
	private User user;
}
