package com.vishwakarma.vastu.model;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity(name="User")
@Table(name="User", uniqueConstraints={
		@UniqueConstraint(columnNames = "email"),
		@UniqueConstraint(columnNames = "verificationCode") })

@Inheritance(strategy=InheritanceType.JOINED)
public class User extends BaseEntity{
	
	@NotNull
	@NotEmpty
	private String firstName;
	
	@NotNull
	@NotEmpty
	private String lastName;
	
	private String address;
	
	private String gender;
	
	
	@Column(unique=true,nullable=false,updatable=false)
	private String username;
	
	@Column(nullable=false)
	private String password;
	
	@Email
	@NotEmpty
	@NotNull
	private String email;
	
	@NotEmpty
	@NotNull
	private String mobileNumber;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "UserRole", joinColumns =@JoinColumn(name = "userId"), inverseJoinColumns =@JoinColumn(name = "roleId"))
	private Set<Role> userRoles;	
	
	@NotNull
	@Column(columnDefinition="tinyint(1) default '1'")
	private boolean isEnabled;
	
	@NotNull
	@Column(columnDefinition="tinyint(1) default '0'")
	private boolean isVerified;
	
	private String verificationCode;
	
	private Date dob;

	private String profilePicUrl;
	
	@Transient
	private byte[] profilePicData;
	
	@Transient
	private String fileName;
	
	@Transient
	private Logger logger = LoggerFactory.getLogger(User.class);
	
	@PrePersist
	public void setDefaults() {
		
		if (StringUtils.isEmpty(username)) {
			username = email;
		}
	}
	
	@PostPersist
	public void createProfilePicFile() {
		if(null != profilePicData) {
			try {
				File profilePic = getProfilePicFile();
				FileUtils.writeByteArrayToFile(profilePic, profilePicData);
				setProfilePicUrl(profilePic.getAbsolutePath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Set<Role> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<Role> userRoles) {
		this.userRoles = userRoles;
	}
	
	public boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public boolean getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}
	
	public String getProfilePicUrl() {
		return profilePicUrl;
	}

	public void setProfilePicUrl(String profilePicUrl) {
		this.profilePicUrl = profilePicUrl;
	}

	public byte[] getProfilePicData() {
		return profilePicData;
	}

	/**
	 * Don't forget to call {@linkplain setFileName()}
	 * @param profilePicData
	 */
	public void setProfilePicData(byte[] profilePicData) {
		this.profilePicData = profilePicData;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setUserRole(Role role) {
		this.userRoles = new HashSet<Role>();
		this.userRoles.add(role);
	}
	
	public boolean hasRole(String roleName) {
		for (Role role : userRoles) {
			if (role.getRole().equals(roleName)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns whether user has any role specified in CSV string
	 * @param roleCSV - Comma Separated Role Names
	 * @return
	 */
	public boolean hasAnyRole(String roleCSV) {
		String[] rolesArray = roleCSV.split(",");
		for (int i = 0; i < rolesArray.length; i++) {
			rolesArray[i] = rolesArray[i].trim();
		}
		List<String> roles = Arrays.asList(rolesArray);
		for (Role role : userRoles) {
			if(roles.contains(role.getRole())) {
				return true;
			}
		}
		return false;
	}
	
	public void addRole(Role role) {
		if(this.userRoles == null) {
			this.userRoles = new HashSet<Role>();
		}
		this.userRoles.add(role);
	}
	
	public void removeRole(Role role) {
		if(this.userRoles != null) {
			this.userRoles.remove(role);
		}
	}

	public String getFullName() {
		return firstName+" "+lastName;
	}
	
	@Override
	public String toString() {
		return firstName+" "+lastName;
	}
	
	private File getProfilePicFile() {
		
		File baseDir = new File(System.getProperty("catalina.base"), "userProfilePics");
		if(!baseDir.exists() || !baseDir.isDirectory()) {
			baseDir.mkdir();
		}
		File profilePic = new File(baseDir, this.getId() + "_" + this.getFileName());
		if(profilePic.exists() && !profilePic.isDirectory()) {
			profilePic.delete();
		}
		return profilePic;
	}
	
}
