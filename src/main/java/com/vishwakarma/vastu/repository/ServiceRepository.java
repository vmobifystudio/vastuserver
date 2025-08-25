package com.vishwakarma.vastu.repository;

import com.vishwakarma.vastu.model.Service;

public interface ServiceRepository extends CustomRepository<Service, Long>{

	public Service findByCode(String code);
	
	public Service findByServiceName(String serviceName);
}
