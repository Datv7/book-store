package com.example.bookstore.service;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookstore.configuration.AppException;
import com.example.bookstore.configuration.ErrorCode;
import com.example.bookstore.dto.VoucherDetail;
import com.example.bookstore.dto.VoucherRequest;
import com.example.bookstore.entity.Voucher;
import com.example.bookstore.mapper.VoucherMapper;
import com.example.bookstore.repository.VoucherRepository;
import com.example.bookstore.service.Iservice.IVoucherService;
@Service
public class VoucherService implements IVoucherService{
	@Autowired
	private VoucherRepository voucherRepository;
	@Autowired
	private VoucherMapper voucherMapper;
	
	@Override
	public List<Voucher> getAll() {
		// TODO Auto-generated method stub
		List<Voucher> vouchers=voucherRepository.findAll();
		for(Voucher voucher:vouchers) {
			voucher.setRemainingCount(voucher.getLimitCount()-voucher.getRemainingCount());
		}
		return vouchers;
	}

	@Override
	public void createVoucher(VoucherRequest voucherRequest) {
		// TODO Auto-generated method stub
		boolean check=false;
		String x="";
		do {
			x=UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
			check=voucherRepository.existsByCode(x);
			System.out.println("code voucher "+x);
		}while(check);
		
		Voucher voucher=voucherMapper.toVoucher(voucherRequest);
		voucher.setCode(x);
		voucher.setRemainingCount(voucher.getLimitCount());
		voucher.setActived(true);
		voucherRepository.save(voucher);
		
	}

	@Override
	public void updateVoucher(int id, VoucherRequest voucherRequest) {
		// TODO Auto-generated method stub
		Date now=new Date();
		Voucher voucher=voucherRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.VOUCHER_NOT_EXISTED));
		boolean check=voucher.getStartAt().before(now);
		if(check) throw new AppException(ErrorCode.VOUCHER_ISSUED); 
		voucherMapper.updateVoucher(voucherRequest, voucher);
		voucher.setRemainingCount(voucher.getLimitCount());
		
		voucherRepository.save(voucher);
	}

	@Override
	public void activeVoucher(List<Integer> voucherIds, boolean actived) {
		// TODO Auto-generated method stub
		voucherRepository.changeActiveByListId(new Date(), voucherIds, actived);
	}

	@Override
	public void deleteVoucher(List<Integer> voucherIds) {
		// TODO Auto-generated method stub
		int modifyCount=voucherRepository.deleteVoucher(voucherIds);
		if(modifyCount!=voucherIds.size()) throw new AppException(
				ErrorCode.VOUCHER_PARTIALLY_DELETED, 
				Set.of(String.valueOf(modifyCount),String.valueOf(voucherIds.size()))); 
	}

}
