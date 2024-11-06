package com.example.bookstore.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the orders database table.
 *
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="orderr")
public class Order  {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id",nullable=false, length=50)
	private String id;

	@Column(name = "status",nullable=false, length=50)
	private String status;

	@Column(name = "totalAmount",nullable=false)
	private int totalAmount;
	
	@Column(name = "reason", length=100)
	private String reason;
	
	@Column(name = "creatAt",nullable=false)
	private Date creatAt;

	@Column(name = "updateAt",nullable=false)
	private Date updateAt;

	@ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;

	@OneToMany(mappedBy="orderr")
	private List<Orderitem> orderitems;
	
	@OneToOne(mappedBy = "order")
	private Delivery delivery;
	@ManyToOne
	@JoinColumn(name = "addressId",nullable=false)
	private Address address;


}