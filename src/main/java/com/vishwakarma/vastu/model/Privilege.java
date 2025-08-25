package com.vishwakarma.vastu.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity(name="Privilege")
public class Privilege extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	private String description;

	private String name;
	
	@ManyToMany
    @JoinTable(name="Privileges_Roles", joinColumns= @JoinColumn(name="privilegeId"), inverseJoinColumns=@JoinColumn(name="roleId"))
	private Set<Role> roles;
	
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Privilege other = (Privilege) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}	
	
	/* Access Privilege */
	public static final String PRIV_SUPER_ADMIN = "PRIV_SUPER_ADMIN"; 
	public static final String PRIV_MANAGER = "PRIV_MANAGER";
	public static final String PRIV_CUSTOMER = "PRIV_CUSTOMER";
	public static final String PRIV_BUSINESS_USER = "PRIV_BUSINESS_USER";
}
