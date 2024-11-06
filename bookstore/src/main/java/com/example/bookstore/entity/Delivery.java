package com.example.bookstore.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name="delivery") 
public class Delivery {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id",unique=true, nullable=false, length=50)
	private String id;
	@Column(name = "fee",nullable=false)
	private int fee;
	@Column(name = "date")
	private Date date;
	@Column(name = "status",nullable=false, length=50)
	private String status;
	@Column(name = "count",nullable=false)
	private int count;
	@Column(name = "createAt",nullable=false)
	private Date createAt;
	@OneToOne
	@JoinColumn(name = "orderId",nullable = false)
	private Order order;
}
