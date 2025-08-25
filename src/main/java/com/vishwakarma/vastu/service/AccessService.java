package com.vishwakarma.vastu.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vishwakarma.vastu.messaging.EmailHelper;
import com.vishwakarma.vastu.model.User;
import com.vishwakarma.vastu.utils.NotificationHelper;

@Service
public class AccessService {

	@Autowired
	private NotificationHelper notificationHelper;
	
	@Resource
	private UserService userService;
	
	@Resource
	private EmailHelper emailHelper;

	private final static Logger log = LoggerFactory.getLogger(AccessService.class);

	public boolean recoverUserPassword(String email) {
		String password;
		User user = userService.getUserByEmail(email, true);
		password = userService.recoverPassword(user.getId());
		String messageBody = notificationHelper.getEmailBodyForPasswordReset(user, password);
		try {
			emailHelper.sendSimpleEmail(user.getEmail(), "Password Reset Request", messageBody);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
