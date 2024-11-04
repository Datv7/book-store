package com.example.bookstore.service.imp;

public interface INotification {
	void sendNotification(String fullName,String email,String key,String passwordResetPageUrl);
}
