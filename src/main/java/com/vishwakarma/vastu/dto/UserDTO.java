package com.vishwakarma.vastu.dto;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.vishwakarma.vastu.model.Role;
import com.vishwakarma.vastu.model.User;

/**
 * @author Vishal
 *
 */
public class UserDTO implements Serializable{

	private long id;
	private String firstName, lastName, userName, email, mobileNumber,profilePicUrl;
	private boolean isBusinessUser, isCustomer,isDetailsPopulated,hasFirmRegister;
	
	private boolean isVerified = false;
	
	public UserDTO(User user) {
		this.id = user.getId();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.userName = user.getUsername();
		this.email = user.getEmail();
		this.mobileNumber = user.getMobileNumber();
		if(StringUtils.isNotBlank(user.getProfilePicUrl())){
			this.profilePicUrl = "/restapi/getProfilePicture?email="+user.getEmail();
		}
		//this.profilePicUrl = user.getProfilePicUrl();
		this.isVerified = user.getIsVerified();
		Set<Role> roles = user.getUserRoles();
		
		for (Role role : roles) {
			
			if(role.getRole().equals(Role.ROLE_DEVICE_USER)) {
				this.isCustomer = true;
			}
		}
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getMobileNumber() {
		return mobileNumber;
	}
	
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	
	public String getProfilePicUrl() {
		return profilePicUrl;
	}

	public void setProfilePicUrl(String profilePicUrl) {
		this.profilePicUrl = profilePicUrl;
	}

	public boolean isVerified() {
		return isVerified;
	}
	
	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}
	
	public boolean getIsBusinessUser() {
		return isBusinessUser;
	}
	
	public void setIsBusinessUser(boolean isBusinessUser) {
		this.isBusinessUser = isBusinessUser;
	}
	
	public boolean getIsCustomer() {
		return isCustomer;
	}

	public void setIsCustomer(boolean isCustomer) {
		this.isCustomer = isCustomer;
	}

	public boolean getIsDetailsPopulated() {
		return isDetailsPopulated;
	}

	public void setIsDetailsPopulated(boolean isDetailsPopulated) {
		this.isDetailsPopulated = isDetailsPopulated;
	}

	public boolean getHasFirmRegister() {
		return hasFirmRegister;
	}

	public void setHasFirmRegister(boolean hasFirmRegister) {
		this.hasFirmRegister = hasFirmRegister;
	}
}
