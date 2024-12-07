package com.example.bookstore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.bookstore.dto.PDWProjection;
import com.example.bookstore.entity.District;
@Repository
public interface DistrictRepository extends JpaRepository<District, Integer> {
	Optional<District> findByIdGHN(int idGHN);
	
	@Query("select d from District d join d.province p where p.id=?1 and d.enable=true")
	List<PDWProjection> getDistrict(int provinceId);
}
