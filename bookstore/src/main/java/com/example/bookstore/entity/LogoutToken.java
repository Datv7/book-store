package com.example.bookstore.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "logoutToken")
public class LogoutToken {
	@Id
	@Column(name = "id")
	private String id;
	@Column(name = "expire")
	private Date expire;
}
