package com.vishwakarma.vastu.repository;

import com.vishwakarma.vastu.model.OrderProduct;

public interface OrderProductRepository extends CustomRepository<OrderProduct, Long> {
	
	public OrderProduct findByCode(String code);

}
