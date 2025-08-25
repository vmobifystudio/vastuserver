package com.vishwakarma.vastu.model.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class TipSpecification {

	public static Specification search(final String searchTerm) {
		return new Specification() {
			
			@Override
			public Predicate toPredicate(Root item, CriteriaQuery query, CriteriaBuilder builder) {
				Predicate predicate = builder.or();
				predicate.getExpressions().add(builder.or(builder.like(item.get("tipTitle"), "%" + searchTerm + "%")));

				return predicate;
			}
		};
	}
}
