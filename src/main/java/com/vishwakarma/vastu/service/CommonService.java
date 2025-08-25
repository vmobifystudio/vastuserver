package com.vishwakarma.vastu.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class CommonService {
	
	public Map<String, Long> getObjectCounts(boolean useAccessControl) {
	
		Map<String, Long> counts = new HashMap<String, Long>();
		return counts;
	}
}
