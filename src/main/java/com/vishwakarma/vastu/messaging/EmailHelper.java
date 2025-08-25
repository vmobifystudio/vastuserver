package com.vishwakarma.vastu.messaging;

import javax.annotation.Resource;

import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EmailHelper {

	@Resource
	private JavaMailSenderImpl javaMailSenderImpl;
	
	@Resource
	private Environment environment;
	
	@Async
	public void sendSimpleEmail(String email, String subject, String messageBody) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(environment.getProperty("email.senderName"));
		simpleMailMessage.setTo(email);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(messageBody);
		simpleMailMessage.setReplyTo(environment.getProperty("email.replyTo"));
		javaMailSenderImpl.send(simpleMailMessage);
	}
	
}
