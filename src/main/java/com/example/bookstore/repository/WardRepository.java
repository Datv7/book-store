package com.example.bookstore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.bookstore.dto.PDWProjection;
import com.example.bookstore.entity.Ward;
@Repository
public interface WardRepository extends JpaRepository<Ward, Integer>{
	Optional<Ward> findByIdGHN(int idGHN);
	
	@Query("select w from Ward w join w.district d where d.id=?1 and w.enable=true")
	List<PDWProjection> getWard(int districtId);
}
