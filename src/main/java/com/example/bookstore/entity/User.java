package com.example.bookstore.entity;

import java.util.ArrayList;
import java.util.Date;
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
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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

	@Column(name = "phoneNumber",nullable=false,unique = true, length=15)
	private String phoneNumber;

	@Column(name="password",nullable=false,length = 100)
	private String password;
	
	@Column(name = "urlAvatar",length=255)
	private String urlAvatar;
	@Builder.Default
	@Column(name = "version",nullable=false)
	private int version=1;

	@Column(name = "createAt")
	private Date createAt;
	
	@Column(name = "updateAt")
	private Date updateAt;
	
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

	@PrePersist
	public void persist() {
		Date date=new Date();
		createAt=date;
		updateAt=date;
	}
	@PreUpdate
	public void update() {
		updateAt=new Date();
	}
	
}