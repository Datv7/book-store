package com.example.bookstore.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The primary key class for the cartitem database table.
 *
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Embeddable
public class CartitemPK implements Serializable {
	private static final long serialVersionUID = 1L;
//	@Column(name = "cart_id")
	private Integer cartId;
//	@Column(name = "item_id")
	private String itemId;


}