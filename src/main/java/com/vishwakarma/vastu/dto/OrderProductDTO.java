package com.vishwakarma.vastu.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.vishwakarma.vastu.model.LineItem;
import com.vishwakarma.vastu.model.OrderProduct;
import com.vishwakarma.vastu.model.Product;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_NULL)
public class OrderProductDTO {

	private String code, name, address, mobileNumber;
	
	private int pin, count;
	
	private List<String> product;
	
	OrderProductDTO() {
		
	}
	
	public OrderProductDTO(OrderProduct orderProduct) {
		
		setCode(orderProduct.getCode());
		setName(orderProduct.getName());
		setAddress(orderProduct.getAddress());
		setMobileNumber(orderProduct.getMobileNumber());
		setPin(orderProduct.getPin());
		List<String> lineItems = new ArrayList<String>();
		List<String> products = new ArrayList<String>();
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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<String> getProduct() {
		return product;
	}

	public void setProduct(List<String> product) {
		this.product = product;
	}
}