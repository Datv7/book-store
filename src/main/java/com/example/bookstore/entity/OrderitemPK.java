package com.example.bookstore.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The primary key class for the orderitem database table.
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class OrderitemPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
//	@Column(name = "order_id")
	private String orderId;
//	@Column(name = "item_id")
	private String itemId;

}