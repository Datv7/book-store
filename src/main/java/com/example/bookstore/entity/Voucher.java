package com.example.bookstore.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name= "voucher")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Voucher {
	@Id
	@Column(name = "id",nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "code",nullable = false)
	private String code;
	
	@Column(name = "type",nullable = false)
	private String type;
	@JsonProperty("discountValue")
	@Column(name = "value",nullable = false)
	private int value;
	@JsonProperty("usageLimit")
	@Column(name = "limitCount",nullable = false)
	private int limitCount;
	@JsonProperty("used")
	@Column(name = "remainingCount",nullable = false)
	private int remainingCount;
	@JsonProperty("isActivated")
	@Column(name = "actived",nullable = false)
	private boolean actived;
	@JsonProperty("startDate")
	@Column(name = "startAt")
	private Date startAt;
	@JsonProperty("endDate")
	@Column(name = "endAt")
	private Date endAt;
	@JsonProperty("createdAt")
	@Column(name = "createAt")
	private Date createAt;
	@JsonProperty("updatedAt")
	@Column(name = "updateAt")
	private Date updateAt;
	
	@PrePersist
	public void persist() {
		Date date=new Date();
		createAt=date;
		updateAt=createAt;
	}
	@PreUpdate
	public void update() {
		updateAt=new Date();
	}
}
