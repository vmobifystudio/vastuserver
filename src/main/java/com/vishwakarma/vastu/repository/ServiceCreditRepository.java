package com.vishwakarma.vastu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vishwakarma.vastu.model.ServiceCredit;

public interface ServiceCreditRepository extends JpaRepository<ServiceCredit, Long> {
	public ServiceCredit findByServiceName(String serviceName);
}

