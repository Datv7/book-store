package com.example.bookstore.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the users database table.
 *
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="user")
public class User  {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id",unique=true, nullable=false, length=50)
	private String id;

	@Column(name = "email",nullable=false,unique = true, length=100)
	private String email;

	@Column(name = "fullName",nullable=false, length=100)
	private String fullName;

	@Column(name = "sdt",nullable=false,unique = true, length=15)
	private String sdt;

	@Column(name="password",nullable=false,length = 100)
	private String password;
	
	@Column(name = "urlAvatar",length=255)
	private String urlAvatar;

	@Column(name = "version",nullable=false)
	private int version;

	@Builder.Default
	@OneToMany(mappedBy="user")
	private List<Cart> carts=new ArrayList<Cart>();

	@Builder.Default
	@OneToMany(mappedBy="user")
	private List<Order> orders=new ArrayList<Order>();

	@Builder.Default
	@OneToMany(mappedBy="user")
	private List<Review> reviews=new ArrayList<Review>();
	
	@Builder.Default
	@OneToMany(mappedBy="user")
	private List<Address> address=new ArrayList<Address>();

	@Builder.Default
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
	        name = "userRole",
	        joinColumns = @JoinColumn(name = "user_id"),
	        inverseJoinColumns = @JoinColumn(name = "role_id"),
	        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_id"})
	    )
	private List<Role> roles=new ArrayList<Role>();

}