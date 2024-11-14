package com.example.bookstore.service;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.bookstore.service.Iservice.INotification;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
@Service
public class EmailNotification implements INotification{
	@Value("${spring.mail.username}")
	String from;
	
	@Autowired
	private JavaMailSender javaMailSender;
	@Override
	public void sendNotification(String email,String token, String passwordResetPageUrl) {
		// TODO Auto-generated method stub
		String url=passwordResetPageUrl + "?" + "token="+token + "&email="+email;
		MimeMessage mailMessage=javaMailSender.createMimeMessage();
		MimeMessageHelper messageHelper=new MimeMessageHelper(mailMessage);
		try {
			messageHelper.setFrom(from,"Book Store");
			messageHelper.setTo(email);
			messageHelper.setSubject("Khôi phục mật khẩu");
			messageHelper.setText(
					"<!DOCTYPE html>\r\n"
							+ "<html>\r\n"
							+ "<head>\r\n"
							+ "    <meta charset=\"utf-8\">\r\n"
							+ "    <title>Yêu cầu đặt lại mật khẩu</title>\r\n"
							+ "</head>\r\n"
							+ "<body>\r\n"
							+ "    <h2>Xin Chào,</h2>\r\n"
							+ "    <p>Chúng tôi đã nhận được yêu cầu đặt lại mật khẩu cho tài khoản của bạn.</p>\r\n"
							+ "    \r\n"
							+ "    <p>Để đặt lại mật khẩu, vui lòng nhấp vào liên kết bên dưới.</p>\r\n"
							+ "    \r\n"
							+ "    <p>\r\n"
							+ "        <a href=\""
							+ url
							+ " \" style=\"background-color: #4CAF50; color: white; padding: 10px 15px; text-decoration: none; border-radius: 5px;\">\r\n"
							+ "            Đặt lại mật khẩu\r\n"
							+ "        </a>\r\n"
							+ "    </p>\r\n"
							+ "\r\n"
							+ "    <p>Liên kết này sẽ hết hạn sau 3 phút. Nếu bạn không yêu cầu đặt lại mật khẩu, hãy bỏ qua email này.</p>\r\n"
							+ "    \r\n"
							+ "    <p>Nếu bạn cần thêm hỗ trợ, vui lòng liên hệ với chúng tôi.</p>\r\n"
							+ "\r\n"
							+ "    <p>Trân trọng,<br>Đội ngũ hỗ trợ</p>\r\n"
							+ "</body>\r\n"
							+ "</html>"
					,true);
		} catch (MessagingException|UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		javaMailSender.send(mailMessage);
		
		
		
		
	}
	@Override
	public int sendOtp(String email) {
		int otp = ThreadLocalRandom.current().nextInt(100000, 1000000);
		// TODO Auto-generated method stub
		MimeMessage mailMessage=javaMailSender.createMimeMessage();
		MimeMessageHelper messageHelper=new MimeMessageHelper(mailMessage);
		try {
			messageHelper.setFrom(from,"Book Store");
			messageHelper.setTo(email);
			messageHelper.setSubject("Xác nhận đăng ký tài khoản");
			messageHelper.setText("<!DOCTYPE html>\r\n"
					+ "<html lang=\"vi\">\r\n"
					+ "<head>\r\n"
					+ "    <meta charset=\"UTF-8\">\r\n"
					+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
					+ "</head>\r\n"
					+ "<body style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;\">\r\n"
					+ "    <div style=\"max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);\">\r\n"
					+ "        <h2 style=\"text-align: center; color: #333;\">Xác Thực Đăng Ký Tài Khoản</h2>\r\n"
					+ "        <p>Chào bạn,</p>\r\n"
					+ "        <p>Cảm ơn bạn đã đăng ký tài khoản với chúng tôi. Để hoàn tất quá trình đăng ký, vui lòng nhập mã OTP dưới đây vào hệ thống của chúng tôi:</p>\r\n"
					+ "        <h3 style=\"text-align: center; background-color: #007bff; color: white; padding: 10px; border-radius: 4px;\">\r\n"
					+ "            "+ otp+"\r\n"
					+ "        </h3>\r\n"
					+ "        <p>Mã OTP này sẽ hết hạn sau 3 phút. Nếu bạn không yêu cầu đăng ký tài khoản, vui lòng bỏ qua email này.</p>\r\n"
					+ "        <p>Chúc bạn một ngày tốt lành!</p>\r\n"
					+ "        <p>Trân trọng,</p>\r\n"
					+ "        <p>Đội ngũ hỗ trợ</p>\r\n"
					+ "        <hr style=\"border: 0; border-top: 1px solid #eee;\">\r\n"
					+ "    </div>\r\n"
					+ "</body>\r\n"
					+ "</html>\r\n"
					+ ""
					,true);
		} catch (MessagingException|UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		javaMailSender.send(mailMessage);
		return otp;
	}

}
