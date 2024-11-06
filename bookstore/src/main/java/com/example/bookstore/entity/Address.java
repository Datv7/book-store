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
	
	@Column(name = "districtId",nullable=false)
	private int districtId;
	@Column(name = "provinceId",nullable=false)
	private int provinceId;
	@Column(name = "wardId",nullable=false)
	private int wardId;
	
	@ManyToOne
	@JoinColumn(name = "userId",nullable=false)
	private User user;
}
