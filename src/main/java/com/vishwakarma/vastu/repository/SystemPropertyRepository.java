package com.vishwakarma.vastu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vishwakarma.vastu.model.SystemProperty;

public interface SystemPropertyRepository extends JpaRepository<SystemProperty, Long> {
	public SystemProperty findByPropName(String propName);
}

