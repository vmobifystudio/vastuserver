package com.vishwakarma.vastu.repository;


import org.springframework.data.repository.CrudRepository;

import com.vishwakarma.vastu.model.Role;


public interface RoleRepository extends CrudRepository<Role, Long> {

	public Role findByRole(final String roleName);

}
