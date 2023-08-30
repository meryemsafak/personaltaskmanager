package com.project.webservice.taskmanagerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender emailSender;

	public void sendEmail(String email, String subject, String emailContent) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("meryemvrl@gmail.com");
		message.setTo(email);
		message.setSubject(subject);
		message.setText(emailContent);
		emailSender.send(message);
	}

}
