package com.vishwakarma.vastu.json.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class JsonUser {

	private long id;
	
	private String userId;
	private String userType;
	private String fullName;
	private String batch;
	private String centre;
	private String status;

	@Override
	public boolean equals(Object o) {
		return o instanceof JsonUser && ((JsonUser)o).id==id;
	};
	
	@Override
	public int hashCode() {
		return Long.valueOf(id).hashCode();
	};
	
	public JsonUser() {
	}

	public long getId() {
		return id;
	}

	public void setId(long rowId) {
		this.id = rowId;
	}

	
	public String getUserId() {
		return userId;
	}
	
	//@JsonProperty("id")
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	
	public String getFullName() {
		return fullName;
	}

	@JsonProperty("name")
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getCentre() {
		return centre;
	}

	public void setCentre(String centre) {
		this.centre = centre;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}

