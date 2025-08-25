package com.vishwakarma.vastu.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vishwakarma.vastu.repository.UserRepository;

@Controller
@RequestMapping("/email")
public class EmailController {
	
	final static Logger log = LoggerFactory.getLogger(EmailController.class);

	@Resource 
	private UserRepository userRepository;
	
	@RequestMapping("/compose")
	public String compose() {
		return "email/form";
	}
}
