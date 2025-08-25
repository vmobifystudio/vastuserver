package com.vishwakarma.vastu.model.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class PickListItemSpecifications {

	public static Specification search(final String searchTerm) {
		return new Specification() {

			@Override
			public Predicate toPredicate(Root root, CriteriaQuery query,
					CriteriaBuilder cb) {
				Predicate predicate = cb.or();
				predicate.getExpressions().add(cb.like(root.get("itemValue"), "%" + searchTerm + "%"));
				predicate.getExpressions().add(cb.like(root.get("displayValue"), "%" + searchTerm + "%"));
				return predicate;
			}
		};
	}
	
}
