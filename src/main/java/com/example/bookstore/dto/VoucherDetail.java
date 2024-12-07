package com.example.bookstore.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoucherDetail {
	private Integer id;
	
	private String code;
	
	private String type;
	@JsonProperty("discountValue")
	private int value;
	@JsonProperty("usageLimit")
	private int limitCount;
	private int used;
	@JsonProperty("isActivated")
	private boolean actived;
	@JsonProperty("startDate")
	private Date startAt;
	@JsonProperty("endDate")
	private Date endAt;
	private Date createAt;
	private Date updateAt;
}
