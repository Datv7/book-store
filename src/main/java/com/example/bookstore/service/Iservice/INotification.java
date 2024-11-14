package com.example.bookstore.service.Iservice;

public interface INotification {
	void sendNotification(String email,String token,String resetPasswordPage);
	int sendOtp(String email);
}
