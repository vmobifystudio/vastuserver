package com.vishwakarma.vastu.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.vishwakarma.vastu.model.MessageLog;
import com.vishwakarma.vastu.repository.MessageLogRepository;
import com.vishwakarma.vastu.repository.UserRepository;
import com.vishwakarma.vastu.utils.MessageMode;
import com.vishwakarma.vastu.utils.StringHelper;

@Service
public class MessageLogService {

	@Resource 
	private MessageLogRepository messageLogRepository;
	
	@Resource
	private UserRepository userRepository;

	public void addMessageLog (Long userId, MessageMode mode, String tag, String... destination) {		
		MessageLog log = new MessageLog(userId, mode, StringHelper.arrayToCsv(destination), new Date(), tag);
		messageLogRepository.save(log);
	}

	public Page<MessageLog> getScanLogs(Integer pageNumber, int pageSize) {
		PageRequest request =
	            new PageRequest(pageNumber - 1, pageSize, Sort.Direction.DESC, "id");
		Page<MessageLog> messageLogs = messageLogRepository.findAll(request);
		for (MessageLog log : messageLogs.getContent()) {
			if (userRepository.findOne(log.getSenderUserId()) != null) {
				log.setEmail(userRepository.findOne(log.getSenderUserId()).getEmail());
			}
		}		
		return messageLogs;
	}
}
