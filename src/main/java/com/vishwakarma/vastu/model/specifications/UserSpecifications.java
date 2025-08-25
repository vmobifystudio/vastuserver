package com.vishwakarma.vastu.model.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {

	public static Specification search(final String searchTerm) {
		return new Specification() {
 
			@Override
			public Predicate toPredicate(Root item, CriteriaQuery query,
					CriteriaBuilder builder) {
                Predicate predicate = builder.or();
                predicate.getExpressions().add(builder.or(
                        builder.like(item.get("firstName"), "%"+searchTerm+"%")));
                predicate.getExpressions().add(builder.or(
                        builder.like(item.get("lastName"), "%"+searchTerm+"%")));
                predicate.getExpressions().add(builder.or(
                        builder.like(item.get("email"), "%"+searchTerm+"%")));
                predicate.getExpressions().add(builder.or(
                        builder.like(item.get("username"), "%"+searchTerm+"%")));
                                      
 				return predicate;
			}
			
		};
	}
	
	public static Specification searchByPhoneNumber(final Long userId,final String searchTerm) {
		return new Specification() {
 
			@Override
			public Predicate toPredicate(Root item, CriteriaQuery query,
					CriteriaBuilder builder) {
                Predicate predicate = builder.and();
                predicate.getExpressions().add(builder.or(builder.like(item.get("mobileNumber"), "%"+searchTerm+"%")));
                predicate.getExpressions().add(builder.or(builder.notEqual(item.get("id"), userId)));
 				return predicate;
			}
			
		};
	}
}
