package com.example.bookstore.service.Iservice;

import java.util.List;

import com.example.bookstore.dto.VoucherDetail;
import com.example.bookstore.dto.VoucherRequest;
import com.example.bookstore.entity.Voucher;

public interface IVoucherService {
	List<Voucher> getAll();
	void createVoucher(VoucherRequest voucherRequest);
	void updateVoucher(int id,VoucherRequest voucherRequest);
	
	void activeVoucher(List<Integer> voucherIds,boolean actived);
	
	void deleteVoucher(List<Integer> voucherIds);
}
