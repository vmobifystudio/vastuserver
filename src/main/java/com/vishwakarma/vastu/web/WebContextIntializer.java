package com.vishwakarma.vastu.web;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

@Component
public class WebContextIntializer implements ServletContextAware {

	private ServletContext context;

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.context = servletContext;
	}

	@PostConstruct
	public void setServletContextAttribute() {
	}

}
