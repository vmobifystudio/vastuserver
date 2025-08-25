package com.vishwakarma.vastu.model;

import javax.persistence.Entity;
import javax.persistence.PostPersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "Service", uniqueConstraints = @UniqueConstraint(columnNames = { "code" }))
public class Service extends BaseEntity {

	private String code;

	@NotNull
	@NotEmpty
	private String serviceName;

	@PostPersist
	public void populateCode() {
		setCode("S-" + getId());
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

}
