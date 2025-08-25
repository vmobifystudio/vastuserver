package com.vishwakarma.vastu.model;

import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="LineItem", uniqueConstraints=@UniqueConstraint(columnNames={"code"}))
public class LineItem extends BaseEntity {

	private String code;
	
	private int count;
	
	@ManyToOne
	@JoinColumn(name="productId", referencedColumnName="id", nullable=false)
	private Product product;
	
	@ManyToOne
	@JoinColumn(name="orderProductId", referencedColumnName="id", nullable=false)
	private OrderProduct orderProduct;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public OrderProduct getOrderProduct() {
		return orderProduct;
	}

	public void setOrderProduct(OrderProduct orderProduct) {
		this.orderProduct = orderProduct;
	}
}
