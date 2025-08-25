package com.vishwakarma.vastu.service;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vishwakarma.vastu.exception.ObjectNotFoundException;
import com.vishwakarma.vastu.model.Privilege;
import com.vishwakarma.vastu.model.Role;
import com.vishwakarma.vastu.repository.PrivilegeRepository;
import com.vishwakarma.vastu.repository.RoleRepository;
import com.vishwakarma.vastu.repository.UserRepository;

/**
 * 
 * @author Vishal
 *
 */
@Service
public class PrivilegeService {

	@Autowired
	private UserRepository userRepository;
	
	@Resource 
	private PrivilegeRepository privilegeRepository;
	
	@Resource 
	private RoleRepository roleRepository;
	
	public List<Privilege> getPrivileges() {
		return privilegeRepository.findAll();
	}
	
	public Privilege getPrivilege(String name) {
		return privilegeRepository.findByName(name);
	}

	public Role addPrivilegeToRole(String roleName, String privilegeName) throws ObjectNotFoundException {
		Privilege privilege = privilegeRepository.findByName(privilegeName);
		if (privilege == null) {
			throw new ObjectNotFoundException("Privilege " + privilegeName + " does not exist");
		}
		Role role = roleRepository.findByRole(roleName);
		if (role == null) {
			throw new ObjectNotFoundException("Role " + roleName + " does not exist");
		}
		role.getPrivileges().add(privilege);
		roleRepository.save(role);
		return role;
	}	
	
	public Role removePrivilegeFromRole(Long teamId, Long userId) throws ObjectNotFoundException {
		return null;
	}
	
	public Collection<Role> getPrivilegesForRole(String role) {
		return null;
	}
}
