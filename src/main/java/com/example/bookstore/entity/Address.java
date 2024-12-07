package com.example.bookstore.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="address") 
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id",unique=true, nullable=false, length=50)
	private String id;
	
	@JsonProperty("phoneNumber")
	@Column(name = "sdt",nullable=false, length=20)
	private String sdt;
	@JsonProperty("description")
	@Column(name = "detail",nullable=false, length=200)
	private String detail;
	
	@Column(name = "fullName",length=200)
	private String fullName;
	@Builder.Default
	@Column(name = "isDefault")
	private boolean isDefault=false;
	@Column(name = "isDeleted")
	@Builder.Default
	private boolean isDeleted=false;
	
	@ManyToOne
	@JoinColumn(name="districtId")
	private District district;
	
	@ManyToOne
	@JoinColumn(name="provinceId")
	private Province province;
	
	@ManyToOne
	@JoinColumn(name="wardId")
	private Ward ward;
	
	@ManyToOne
	@JoinColumn(name = "userId",nullable=false)
	private User user;
}
