package com.vishwakarma.vastu.reports;

import javax.annotation.Resource;

import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource({"classpath:com/vishwakarma/vastu/db.properties"})
public class ReportScheduler {
	
	@Resource 
	private Environment env;
}
