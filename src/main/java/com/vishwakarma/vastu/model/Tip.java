package com.vishwakarma.vastu.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PostPersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="Tip",uniqueConstraints=@UniqueConstraint(columnNames={"code"}))
public class Tip extends BaseEntity {

	private String code;
	
	@Column(columnDefinition="text default null")
	private String tipDescription;

	private String tipTitle;
	
	@PostPersist
	public void populateCode() {
		setCode("T-" + getId());
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTipDescription() {
		return tipDescription;
	}

	public void setTipDescription(String tipDescription) {
		this.tipDescription = tipDescription;
	}

	public String getTipTitle() {
		return tipTitle;
	}

	public void setTipTitle(String tipTitle) {
		this.tipTitle = tipTitle;
	}
	
}
