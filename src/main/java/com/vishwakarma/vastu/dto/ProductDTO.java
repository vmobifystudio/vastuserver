package com.vishwakarma.vastu.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.vishwakarma.vastu.model.Product;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class ProductDTO implements Serializable {

	private String code;
	
	private String productName;
	
	private double price;
	
	private String description;

	private String productPicUrl;
	
	ProductDTO() {
		
	}
	
	public ProductDTO(Product product) {
		
		setCode(product.getCode());
		setProductName(product.getProductName());
		setPrice(product.getPrice());
		setDescription(product.getDescription());
		setProductPicUrl(product.getProductPicUrl());
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProductPicUrl() {
		return productPicUrl;
	}

	public void setProductPicUrl(String productPicUrl) {
		this.productPicUrl = productPicUrl;
	}
}
