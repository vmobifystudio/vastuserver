package com.vishwakarma.vastu.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.vishwakarma.vastu.model.Service;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class ServiceDTO implements Serializable{

	private String serviceName, code;
	
	ServiceDTO() {
		
	}
	
	public ServiceDTO(Service service) {
		setCode(service.getCode());
		setServiceName(service.getServiceName());
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
