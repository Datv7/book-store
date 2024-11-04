package com.example.bookstore.service;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.bookstore.service.imp.INotification;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
@Service
public class EmailNotification implements INotification{
	@Value("${spring.mail.username}")
	String from;
	
	@Autowired
	private JavaMailSender javaMailSender;
	@Override
	public void sendNotification(String fullName,String email,String key, String passwordResetPageUrl) {
		// TODO Auto-generated method stub
		String query=key+"&fullName="+fullName;
		MimeMessage mailMessage=javaMailSender.createMimeMessage();
		MimeMessageHelper messageHelper=new MimeMessageHelper(mailMessage);
		try {
			messageHelper.setFrom(from,"Book Store");
			messageHelper.setTo(email);
			messageHelper.setSubject("Có cái pass cũng quên?");
			messageHelper.setText(
					"<!DOCTYPE html>\r\n"
							+ "<html>\r\n"
							+ "<head>\r\n"
							+ "    <meta charset=\"utf-8\">\r\n"
							+ "    <title>Yêu cầu đặt lại mật khẩu</title>\r\n"
							+ "</head>\r\n"
							+ "<body>\r\n"
							+ "    <h2>Xin Chào,</h2>\r\n"
							+ "    <p>Chúng tôi đã nhận được yêu cầu đặt lại mật khẩu cho tài khoản của bạn tại <strong>[Tên Ứng Dụng]</strong>.</p>\r\n"
							+ "    \r\n"
							+ "    <p>Để đặt lại mật khẩu, vui lòng nhấp vào liên kết bên dưới. Lưu ý rằng bạn chỉ có thể đổi mật khẩu trên thiết bị mà bạn đã sử dụng để gửi yêu cầu này.</p>\r\n"
							+ "    \r\n"
							+ "    <p>\r\n"
							+ "        <a href=\""
							+ passwordResetPageUrl
							+ "?key="
							+ query
							+ " \" style=\"background-color: #4CAF50; color: white; padding: 10px 15px; text-decoration: none; border-radius: 5px;\">\r\n"
							+ "            Đặt lại mật khẩu\r\n"
							+ "        </a>\r\n"
							+ "    </p>\r\n"
							+ "\r\n"
							+ "    <p>Liên kết này sẽ hết hạn sau 3 phút. Nếu bạn không yêu cầu đặt lại mật khẩu, hãy bỏ qua email này.</p>\r\n"
							+ "    \r\n"
							+ "    <p>Nếu bạn cần thêm hỗ trợ, vui lòng liên hệ với chúng tôi.</p>\r\n"
							+ "\r\n"
							+ "    <p>Trân trọng,<br>Đội ngũ hỗ trợ [Tên Ứng Dụng]</p>\r\n"
							+ "</body>\r\n"
							+ "</html>"
					,true);
		} catch (MessagingException|UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		javaMailSender.send(mailMessage);
		
		
		
		
	}

}
