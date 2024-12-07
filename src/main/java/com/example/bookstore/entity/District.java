package com.example.bookstore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name="district")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class District {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "idGhn", nullable=false)
	private int idGHN;
	
	@Column(name = "name", nullable=false)
	private String name;
	
	@Column(name = "isEnable")
	private boolean enable;
	
	@ManyToOne
	@JoinColumn(name="provinceId")
	private Province province;
}
