package com.example.bookstore.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ReviewPK implements Serializable{

	private static final long serialVersionUID = 1L;

	private String userId;

	private String itemId;
}
