package com.vishwakarma.vastu.repository;

import com.vishwakarma.vastu.model.Tip;

public interface TipRepository extends CustomRepository<Tip, Long>{
	
	public Tip findByCode(String code);
	
	public Tip findByTipTitle(String tipTitle);
}
