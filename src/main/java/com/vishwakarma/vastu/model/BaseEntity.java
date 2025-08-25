package com.vishwakarma.vastu.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.springframework.security.core.context.SecurityContextHolder;

@MappedSuperclass
public abstract class BaseEntity implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = true, updatable = false)
	private Date createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = false, updatable = true)
	private Date modifiedDate;
	
	@Column(insertable = false, updatable = true)
	private String lastModifiedBy;
	
	@Column(insertable = true, updatable = false)
	private String createdBy;
	
	@Version
	private Long version;
	
	@Transient
	private boolean updateOperation = false; //HACK: Used by validator to determine if we're updating from UI
	
	@Transient
	private String cipher;
	
	@Transient
	private boolean isEditAllowed = false;
	
	@Transient
	private boolean isDeleteAllowed = false;
	
	@PreUpdate
	public void setUpdatedAuditFields() {
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			return;
		}
		Object userInfo = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		setModifiedDate(new Date());
		setLastModifiedBy(userInfo instanceof UserInfo ? ((UserInfo)userInfo).getUsername() : userInfo.toString());
	
	}
		
	@PrePersist
	public void setAuditFields() {
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			return;
		}
		Object userInfo = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		setCreatedDate(new Date());
		setCreatedBy(userInfo instanceof UserInfo ? ((UserInfo)userInfo).getUsername() : userInfo.toString());
	}
			
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}

	public boolean isUpdateOperation() {
		return updateOperation;
	}
	public void setUpdateOperation(boolean updateOperation) {
		this.updateOperation = updateOperation;
	}

	public String getCipher() {
		return cipher;
	}

	public void setCipher(String cipher) {
		this.cipher = cipher;
	}

	public boolean getIsEditAllowed() {
		return isEditAllowed;
	}

	public void setIsEditAllowed(boolean isEditAllowed) {
		this.isEditAllowed = isEditAllowed;
	}

	public boolean getIsDeleteAllowed() {
		return isDeleteAllowed;
	}

	public void setIsDeleteAllowed(boolean isDeleteAllowed) {
		this.isDeleteAllowed = isDeleteAllowed;
	}
}
