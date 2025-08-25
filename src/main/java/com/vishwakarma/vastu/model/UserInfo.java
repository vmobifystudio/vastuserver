package com.vishwakarma.vastu.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@SuppressWarnings("serial")
public class UserInfo extends org.springframework.security.core.userdetails.User {

	private final User user;
	
	private final Long id;

	private final String firstName;

	private final String lastName;

	private final String email;
	
	private final String userRole;
    
	private Collection<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();

	public UserInfo(User member,boolean enabled,boolean accountNonExpired,boolean credentialsNonExpired,boolean accountNonLocked ) {
		super(member.getEmail(), member.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, getGrantedAuthorities(member.getUserRoles()));
		this.user = member;
		this.id = member.getId();
		this.firstName = member.getFirstName();
		this.lastName = member.getLastName();
		this.email = member.getEmail();
		this.userRole=member.getUserRoles().toArray(new Role[0])[0].getRole();
		for (Role role : member.getUserRoles()) {
			for (Privilege privilege : role.getPrivileges()) {
				grantedAuthorities.add(new SimpleGrantedAuthority(privilege.getName()));
			}
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
		}
	}
	
	public static List<GrantedAuthority> getGrantedAuthorities(Set<Role> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (Role role : roles) {
			for (Privilege privilege : role.getPrivileges()) {
				authorities.add(new SimpleGrantedAuthority(privilege.getName()));
			}
			authorities.add(new SimpleGrantedAuthority(role.getRole()));
		}
		return authorities;
	}

	/**
	 * The internal identifier of this account. Unique across all accounts. When
	 * possible, kept internal and not shared with members.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * The first name of the person associated with this account.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * The last name, or surname, of the person associated with this account.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Convenience operation that derives the full name of the account holder by
	 * combining his or her first and last names.
	 */
	public String getFullName() {
		return firstName + " " + lastName;
	}

	/**
	 * The email address on file for this account. May be unique across all
	 * accounts. If so, may be used as a credential in user authentication
	 * scenarios.
	 */
	public String getEmail() {
		return email;
	}
	
	public String getName() {
		return id.toString();
	}

	public Collection<GrantedAuthority> getGrantedAuthority() {
		return grantedAuthorities;
	}

	public void setGrantedAuthority(Collection<GrantedAuthority> grantedAuthority) {
		this.grantedAuthorities = grantedAuthority;
	}
	
	public String getUserRole() {
		return userRole;
	}
	
	public User getUser(){
		return user;
	}
}