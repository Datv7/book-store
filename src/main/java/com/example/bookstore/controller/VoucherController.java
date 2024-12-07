package com.example.bookstore.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookstore.dto.ApiRespond;
import com.example.bookstore.dto.VoucherDetail;
import com.example.bookstore.dto.VoucherRequest;
import com.example.bookstore.entity.Voucher;
import com.example.bookstore.service.Iservice.IVoucherService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@SecurityRequirement(name = "Bearer Authentication")
@RestController
public class VoucherController {
	@Autowired
	private IVoucherService iVoucherService;
	
	@GetMapping("/admin/vouchers")
	public ApiRespond<List<Voucher>> getAll(){
		return ApiRespond.<List<Voucher>>builder()
				.results(iVoucherService.getAll())
				.build();
	}
	
	@PostMapping("/admin/vouchers")
	public ApiRespond<String> createVoucher(@Valid @RequestBody VoucherRequest voucherRequest){
		iVoucherService.createVoucher(voucherRequest);
		return ApiRespond.<String>builder().build();
	}
	
	@PutMapping("/admin/vouchers/{id}")
	public ApiRespond<String> updateVoucher(@PathVariable("id") int id,@Valid @RequestBody VoucherRequest voucherRequest){
		iVoucherService.updateVoucher(id, voucherRequest);
		return ApiRespond.<String>builder().build();
	}
	@PutMapping("/admin/vouchers/toggle-active")
	public ApiRespond<String> activeVoucher(@RequestBody Map<String, Object> json){
		List<Integer> list =(ArrayList<Integer>)json.get("voucherIds");
		boolean actived=(boolean)json.get("actived");
		iVoucherService.activeVoucher(list, actived);
		return ApiRespond.<String>builder().build();
		
	}
	@DeleteMapping("/admin/vouchers")
	public ApiRespond<String> deleteVoucher(@RequestBody Map<String, Object> json){
		List<Integer> list =(ArrayList<Integer>)json.get("voucherIds");
		iVoucherService.deleteVoucher(list);
		return ApiRespond.<String>builder().build();
	}
}
