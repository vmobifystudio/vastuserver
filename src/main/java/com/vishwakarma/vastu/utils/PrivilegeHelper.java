package com.vishwakarma.vastu.utils;

import javax.annotation.Resource;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.vishwakarma.vastu.model.Role;
import com.vishwakarma.vastu.model.User;
import com.vishwakarma.vastu.model.UserInfo;
import com.vishwakarma.vastu.service.PrivilegeService;
import com.vishwakarma.vastu.service.UserService;

@Component
public class PrivilegeHelper {
	
	@Resource 
	private PrivilegeService privilegeService;
	
	@Resource 
	private UserService userService;
	
	public boolean hasPrivilege(Long userId, String privilege) {
		User user = userService.getUser(userId, true);
		for(Role role : user.getUserRoles()) {
			if(role.getPrivileges().toString().contains(privilege)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean loggedInUserHasPrivilege(String privilege) {
		UserInfo userInfo = (UserInfo)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		for(Role role : userInfo.getUser().getUserRoles()) {
			if(role.getPrivileges().toString().contains(privilege)) {
				return true;
			}
		}
		return false;
	}
	
}
