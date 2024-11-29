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

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="payment")
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id",unique=true, nullable=false, length=50)
	private String id;
	
	@Column(name = "status",nullable=false)
	private String status;
	
	@Column(name = "type",nullable=false)
	private String type;
	
	@Column(name = "date")
	private Date date;
	
	@OneToOne
	@JoinColumn(name = "orderId")
	private Order order;
}
