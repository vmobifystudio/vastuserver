package com.vishwakarma.vastu.service;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vishwakarma.vastu.model.UserInfo;
import com.vishwakarma.vastu.repository.UserRepository;

/**
 * A custom {@link UserDetailsService} where user information
 * is retrieved from a JPA repository
 */
@Service
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {
	
	
	@Resource 
	private UserRepository userDao;

	/**
	 * Returns a populated {@link UserDetails} object. 
	 * The email is first retrieved from the database and then mapped to 
	 * a {@link UserDetails} object.
	 */
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
			com.vishwakarma.vastu.model.User domainUser = userDao.findByEmail(email);
			
			boolean enabled = domainUser.getIsEnabled();
			boolean accountNonExpired = true;
			boolean credentialsNonExpired = true;
			boolean accountNonLocked = true;
			
			return new UserInfo(
					domainUser,
					enabled,
					accountNonExpired,
					credentialsNonExpired,
					accountNonLocked
					);
		
	}
	
}
