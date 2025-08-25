package com.vishwakarma.vastu.controller;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vishwakarma.vastu.model.MessageLog;
import com.vishwakarma.vastu.repository.MessageLogRepository;
import com.vishwakarma.vastu.repository.UserRepository;
import com.vishwakarma.vastu.service.MessageLogService;

@Controller
public class MessageLogController {

	@Resource 
	private MessageLogRepository messageLogRepository;
	
	@Resource
	private MessageLogService messageLogService;

	@Resource 
	private UserRepository userRepository;
	
	private static final int PAGE_SIZE = 50;
	
	@RequestMapping("/messagelog/list")
	public String messageLogList(final Model model,
			@RequestParam(defaultValue="1") Integer pageNumber) {
		Page<MessageLog> page = messageLogService.getScanLogs(pageNumber, PAGE_SIZE);
	    int current = page.getNumber() + 1;
	    int begin = Math.max(1, current - 5);
	    int end = Math.min(begin + PAGE_SIZE, page.getTotalPages());

	    model.addAttribute("page", page);
	    model.addAttribute("beginIndex", begin);
	    model.addAttribute("endIndex", end);
	    model.addAttribute("currentIndex", current);
	    model.addAttribute("messageLogs", page.getContent());
	    return "/messagelog/list";
	}
	
}
