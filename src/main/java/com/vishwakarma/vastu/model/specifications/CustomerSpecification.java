package com.vishwakarma.vastu.model.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class CustomerSpecification {

	public static Specification search(final String searchTerm) {
		
		return new Specification() {
			
			@Override
			public Predicate toPredicate(Root item, CriteriaQuery query, CriteriaBuilder builder) {
				
				Predicate predicate = builder.or();
				predicate.getExpressions().add(builder.or(builder.like(item.get("email"), "%" + searchTerm + "%")));
				return predicate;
			}
		};
	}
	
	public static Specification searchByMerchantId(final Long merchantId) {
		return new Specification() {
			
			@Override
			public Predicate toPredicate(Root item, CriteriaQuery query, CriteriaBuilder builder) {
				Predicate predicate = builder.or();
				Join collectionJoin = item.join("subscribedMerchants");
				predicate.getExpressions().add(builder.or(collectionJoin.get("id").in(merchantId)));
				return predicate;
			}
		};
	}
}
