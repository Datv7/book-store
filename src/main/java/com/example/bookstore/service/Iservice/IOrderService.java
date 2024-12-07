package com.example.bookstore.service.Iservice;


import java.util.Date;
import java.util.Set;

public interface IOrderService {
	Set<String> genOrder(Date start,Date end,int quanity,boolean pending);
}
