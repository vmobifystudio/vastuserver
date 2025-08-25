package com.vishwakarma.vastu.repository;

import com.vishwakarma.vastu.model.Product;

public interface ProductRepository extends CustomRepository<Product, Long>{

	public Product findByCode(String code);
	
	public Product findByProductName(String productName);
}
