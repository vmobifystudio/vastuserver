package com.vishwakarma.vastu.repository;

import com.vishwakarma.vastu.model.LineItem;

public interface LineItemRepository extends CustomRepository<LineItem, Long> {

	public LineItem findByCode(String code);
	
}
