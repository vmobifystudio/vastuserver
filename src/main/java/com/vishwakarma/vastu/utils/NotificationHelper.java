package com.vishwakarma.vastu.utils;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vishwakarma.vastu.messaging.EmailHelper;
import com.vishwakarma.vastu.model.User;

@Component
public class NotificationHelper {

	@Autowired 
	private SystemPropertyHelper systemPropertyHelper;
	
	@Resource 
	private EmailHelper emailHelper;
	
	public void sendNotificationForNewAccount(User user, String password) {
		emailHelper.sendSimpleEmail(user.getEmail(), "Welcome to vastu", getEmailBodyForNewAccount(user, password));
	}
	
	public String getSmsBodyForNewAccount(User user, String password) {
		String smsText;
		smsText = "Your account is successfully created on vastu. Your Username is " +  user.getEmail() +
				"and Password is " + password;
		return smsText;
	}
	
	public String getSmsBodyForPasswordReset(User user, String password) {
		String smsText;
		smsText = "Your password is successfully changed for vastu Account. Your Username is " +  user.getEmail() +
				"and new Password is " + password;
		return smsText;
	}
	
	public String getEmailBodyForNewAccount(User user, String password) {
		String emailBody;
		emailBody = "Hello " + user.getFullName() + ",\n\n" + 
				"Thanks for signing up with vastu. We're really excited to have you onboard!." + "\n\n" + 
				"Your account details are as follows:-" + "\n\n" + 
				"Username- " + user.getEmail() + "\n" +
				"Password- " + password + "\n\n" +
				"You can use vastu as an Individual user to connect with businesses"+
				"worldwide as well as use vastu as a business user to connect with your"+
				"existing and new customers. You will see that vastu is an extremely easy to use and very useful Mobile App."+"\n\n"+
				"As an individual user you can seek customer support from various businesses"+
				"easily, find and order products and services from various small and large"+
				"businesses, order food from Restaurants, find doctors, florists, handymen,"+
				"architects and millions of other small and local businesses."+"\n\n"+
				"As a business user, your existing customers will be able to chat with you"+
				"for customer service and new customers will be able to find you to order"+
				"your products and services. You will be able to create happier customers as"+
				"well as get many new customers. vastu will help your Business grow."+"\n\n"+
				"Best Regards,"+"\n"+
				"Team vastu";
		return emailBody;
	}
	
	public String getEmailBodyForPasswordReset(User user, String password) {
		String emailBody;
		emailBody = "Hi " + user.getFullName() + ",\n" + 
					"Your password has been successfully changed for your vastu account." + "\n" + 
					"Your new account credentials are as follows-" + "\n" +
					"Username- " + user.getEmail() + "\n" +
					"Password- " + password;
		return emailBody;
	}

}
