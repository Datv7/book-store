package com.example.bookstore.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the orderitem database table.
 *
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="orderitem")
public class Orderitem  {

	@EmbeddedId
	private OrderitemPK id;

	@Column(name = "quantity",nullable=false)
	private int quantity;

	@MapsId("itemId")
	@ManyToOne
	@JoinColumn(name="item_id")
	private Item item;

	@MapsId("orderId")
	@ManyToOne
	@JoinColumn(name="order_id")
	private Order orderr;


}