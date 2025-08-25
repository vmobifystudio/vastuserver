package com.vishwakarma.vastu.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vishwakarma.vastu.model.ServiceCredit;
import com.vishwakarma.vastu.repository.ServiceCreditRepository;

@Service
public class ServiceCreditHelper {

	@Resource 
	private ServiceCreditRepository serviceCreditRepository;
	
	public int getAvailableCredits (String serviceName) {
		return serviceCreditRepository.findByServiceName(serviceName).getCredits();
	}
	
	public void addCredits (String serviceName, int credits) {
		ServiceCredit serviceCredit = serviceCreditRepository.findByServiceName(serviceName);
		int availableCredits = getAvailableCredits (serviceName);
		availableCredits = availableCredits + credits;
		
		serviceCredit.setCredits(availableCredits);
		serviceCreditRepository.save(serviceCredit);
	}
	
	public void removeCredits (String serviceName, int credits) {
		ServiceCredit serviceCredit = serviceCreditRepository.findByServiceName(serviceName);
		int availableCredits = getAvailableCredits (serviceName);
		availableCredits = availableCredits - credits;
		
		serviceCredit.setCredits(availableCredits);
		serviceCreditRepository.save(serviceCredit);
	}
}
