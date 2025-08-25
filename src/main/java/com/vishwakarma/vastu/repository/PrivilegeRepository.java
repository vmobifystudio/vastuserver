package com.vishwakarma.vastu.repository;

import com.vishwakarma.vastu.model.Privilege;


public interface PrivilegeRepository extends CustomRepository<Privilege, Long> {

	public Privilege findByName(final String name);
	
}
