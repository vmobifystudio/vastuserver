package com.vishwakarma.vastu.model;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PostPersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name ="OrderProduct" ,uniqueConstraints=@UniqueConstraint(columnNames={"code"}))
public class OrderProduct extends BaseEntity {
	
	private String code;
	
	private String name, address, mobileNumber;

	private int pin;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "Orders_Lineitems",
				joinColumns = @JoinColumn(name = "OrderId", referencedColumnName = "id", nullable = false), 
				inverseJoinColumns = @JoinColumn(name = "LineItemId", referencedColumnName = "id", nullable = false))
	private Set<LineItem> lineItems;
	
	@PostPersist
	public void populateCode() {
		setCode("O-" + getId());
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public Set<LineItem> getLineItems() {
		return lineItems;
	}

	public void setLineItems(Set<LineItem> lineItems) {
		this.lineItems = lineItems;
	}

	public void addLineItems(LineItem lineItem) {
		if (null == lineItems) {
			lineItems = new HashSet<LineItem>();
		}
		lineItems.add(lineItem);
	}
}
