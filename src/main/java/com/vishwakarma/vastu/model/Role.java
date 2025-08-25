package com.vishwakarma.vastu.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity(name="Role")
public class Role extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	private String description;

	private String role;
	
	@ManyToMany(mappedBy = "userRoles",cascade=CascadeType.ALL)
	private Set<User>  users;

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="Privileges_Roles", joinColumns= @JoinColumn(name="roleId"), inverseJoinColumns= @JoinColumn(name="privilegeId"))
	private Set<Privilege>  privileges;

	public Role(){
	}


	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Set<Privilege> privileges) {
		this.privileges = privileges;
	}

	@Override
	public String toString() {
		return role;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		return true;
	}
	
	public static final String ROLE_SUPER_ADMIN = "ROLE_SUPER_ADMIN";
	public static final String ROLE_DEVICE_USER = "ROLE_DEVICE_USER";

}
