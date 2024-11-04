package com.example.bookstore.entity;

import java.io.Serializable;
import java.util.List;

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
//	@Column(name = "carts_id")
	private Integer cartsId;
//	@Column(name = "items_id")
	private String itemsId;


}