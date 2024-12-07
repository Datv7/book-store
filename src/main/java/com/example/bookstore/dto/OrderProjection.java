package com.example.bookstore.dto;

import java.util.Date;
import java.util.List;

public interface OrderProjection {

	String getId(); 
    String getCode();    
    String getName();    
    int getTotalItem();    
    int getTotalAmount();    
    int getShippingFee();    
    Date getCreatedAt();    
    Date getUpdatedAt();    
    PaymentProjection getPayment();    
    List<StatusProjection> getLogs();    
    VoucherProjection getVoucher();
}
