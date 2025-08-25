package com.vishwakarma.vastu.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.vishwakarma.vastu.model.Role;
import com.vishwakarma.vastu.repository.RoleRepository;

@Component
public class RoleCache {

	@Resource
	private RoleRepository roleRepository;
	
	private Map<String, Role> roleMap;
	private boolean initialized = false;
	
	public RoleCache() {
	}
	
//	@Bean
//	@Scope(value = BeanDefinition.SCOPE_SINGLETON)
//	public RoleCache publicInstance() {
//		return new RoleCache();
//	}
	
	private void init() {
		roleMap = new HashMap<String, Role>();
		Iterator<Role> iter = roleRepository.findAll().iterator();
		while (iter.hasNext()) {
			Role role = iter.next();
			roleMap.put(role.getRole(), role);
		}		
		initialized = true;
	}
	
	public Role getRole(String roleName) {
		if (!initialized) {
			init();
		}
		return roleMap.get(roleName);
	}
	
}
