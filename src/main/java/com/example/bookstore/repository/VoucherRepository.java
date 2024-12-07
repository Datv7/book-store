package com.example.bookstore.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookstore.entity.Voucher;
@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Integer>{
	boolean existsByCode(String code);
	
	@Query("update Voucher v set v.actived =?3,v.updateAt=?1 where v.id in ?2")
	@Transactional
	@Modifying
	void changeActiveByListId(Date updateAt,List<Integer> voucherIds,boolean actived);
	
	@Query("delete Voucher v where v.id in ?1")
	@Transactional
	@Modifying
	int deleteVoucher(List<Integer> voucherIds);
}
