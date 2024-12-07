package com.example.bookstore.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.bookstore.dto.ItemDetail;
import com.example.bookstore.dto.VoucherDetail;
import com.example.bookstore.dto.VoucherRequest;
import com.example.bookstore.entity.Item;
import com.example.bookstore.entity.Voucher;

@Mapper(componentModel = "spring")
public interface VoucherMapper {
	VoucherDetail toVoucherDetail(Voucher voucher);
	Voucher toVoucher(VoucherRequest voucherRequest);
	
	void updateVoucher(VoucherRequest voucherRequest,@MappingTarget Voucher voucher);
}
