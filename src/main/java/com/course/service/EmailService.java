package com.course.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	public void sendEmail(String to , String subject , String text) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		 
	    try {
	    	
	    	message.setTo(to);
			message.setSubject(subject);
			message.setText(text);
			message.setFrom("noreply@test.com");
			
			mailSender.send(message);
	    	
	    }catch (Exception e){
	    	System.out.println("寄信失敗：" + e.getMessage());
	    };
		
	}
	
}
