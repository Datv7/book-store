package com.example.bookstore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.bookstore.dto.PDWProjection;
import com.example.bookstore.entity.Province;
@Repository
public interface ProvinceRepository extends JpaRepository<Province, Integer>{
	Optional<Province> findByIdGHN(int idGHN);
	
	@Query("select p from Province p where p.enable=true")
	List<PDWProjection> getProvince();
}
