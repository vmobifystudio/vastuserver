package com.vishwakarma.vastu.model.specifications;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

	public static Specification search(final String searchTerm) {
		return new Specification() {
			
			@Override
			public Predicate toPredicate(Root item, CriteriaQuery query, CriteriaBuilder builder) {
				Predicate predicate = builder.or();
				predicate.getExpressions().add(builder.or(builder.like(item.get("productName"), "%" + searchTerm + "%")));

				return predicate;
			}
		};
	}
	
	public static Specification searchProductByCount(final Integer count) {
		return new Specification() {
			
			@Override
			public Predicate toPredicate(Root item, CriteriaQuery query, CriteriaBuilder builder) {
				
				Predicate fromCountPredicate = null;
				Predicate toCountPredicate = null;
				
				if(count != null) {
					fromCountPredicate = builder.or();
					toCountPredicate = builder.or();
					fromCountPredicate.getExpressions().add(builder.greaterThanOrEqualTo(item.get("fromCount"), count));
					toCountPredicate.getExpressions().add(builder.lessThan(item.get("toCount"), count));
				}
				
				if(null != fromCountPredicate && null != toCountPredicate) {
					return builder.and(fromCountPredicate, toCountPredicate);
				} else if(null != fromCountPredicate) {
					return fromCountPredicate;
				} else if(null != toCountPredicate) {
					return toCountPredicate;
				}
				return builder.or();
			}
		};
	}
	
	public static Specification searchProductByCount1(final HashMap<String, Integer> hashMap) {
		return new Specification() {
			
			@Override
			public Predicate toPredicate(Root item, CriteriaQuery query, CriteriaBuilder builder) {
				
				List<Predicate> predicates = new ArrayList<Predicate>();
				Predicate isKitchenPredicate = builder.or();
				Predicate isBedRoomPredicate = builder.or();
				Predicate isDinningRoomPredicate = builder.or();
				Predicate isPoojaRoomPredicate = builder.or();
				Predicate isLivingRoomPredicate = builder.or();
				Predicate isEnterencePredicate = builder.or();
				
				
				Set set = hashMap.entrySet();
				Iterator itr = set.iterator();
				while(itr.hasNext()) {
					Map.Entry me = (Map.Entry)itr.next();
					String tileName = (String) me.getKey();
					if(tileName == "kitchen") {
						
						isKitchenPredicate.getExpressions().add(builder.isTrue(item.get("isKitchenroomScore")));
						predicates.add(isKitchenPredicate);
						
					} 
					if(tileName == "bedroom") {
						
						isBedRoomPredicate.getExpressions().add(builder.isTrue(item.get("isBedroomScore")));
						predicates.add(isBedRoomPredicate);
						
					} 
					if(tileName == "dinning") {
						
						isDinningRoomPredicate.getExpressions().add(builder.isTrue(item.get("isDinningroomScore")));
						predicates.add(isDinningRoomPredicate);
						
					} 
					if(tileName == "pooja") {
						
						isPoojaRoomPredicate.getExpressions().add(builder.isTrue(item.get("isPoojaroomScore")));
						predicates.add(isPoojaRoomPredicate);
						
					} 
					if(tileName == "living") {
						
						isLivingRoomPredicate.getExpressions().add(builder.isTrue(item.get("isLivingroomScore")));
						predicates.add(isLivingRoomPredicate);
						
					} 
					if(tileName == "enterence") {
						
						isEnterencePredicate.getExpressions().add(builder.isTrue(item.get("isEnterenceScore")));
						predicates.add(isEnterencePredicate);
						
					}
					
				}
				
				if(!predicates.isEmpty()) {
					Predicate[] predicates2 = new Predicate[predicates.size()];
					predicates.toArray(predicates2);
					return  builder.or(predicates2);
				}
				return builder.or();
				
			}
		};
	}
	
	public static Specification searchProductByBathRoom(final boolean isBathRoom) {
		return new Specification() {
			
			@Override
			public Predicate toPredicate(Root item, CriteriaQuery query, CriteriaBuilder builder) {
				
				Predicate isBathRoomPredicate = builder.or();
				if(isBathRoom == false) {
					isBathRoomPredicate.getExpressions().add(builder.isTrue(item.get("isBathRoomScore")));
				} 
				return isBathRoomPredicate;
			}
		};
	}
	
	public static Specification searchProductByKitchen(final boolean isKitchen) {
		return new Specification() {
			
			@Override
			public Predicate toPredicate(Root item, CriteriaQuery query, CriteriaBuilder builder) {
				
				Predicate isKitchenPredicate = builder.or();
				if(isKitchen == false) {
					isKitchenPredicate.getExpressions().add(builder.isTrue(item.get("isKitchenroomScore")));
				} 
				return isKitchenPredicate;
			}
		};
	}
	
	public static Specification searchProductByBedRoom(final boolean isBedRoom) {
		return new Specification() {
			
			@Override
			public Predicate toPredicate(Root item, CriteriaQuery query, CriteriaBuilder builder) {
				
				Predicate isBedRoomPredicate = builder.or();
				if(isBedRoom == false) {
					isBedRoomPredicate.getExpressions().add(builder.isTrue(item.get("isBedroomScore")));
				} 
				return isBedRoomPredicate;
			}
		};
	}
	
	public static Specification searchProductByHall(final boolean isHall) {
		return new Specification() {
			
			@Override
			public Predicate toPredicate(Root item, CriteriaQuery query, CriteriaBuilder builder) {
				
				Predicate isHallPredicate = builder.or();
				if(isHall == false) {
					isHallPredicate.getExpressions().add(builder.isTrue(item.get("isHallScore")));
				} 
				return isHallPredicate;
			}
		};
	}
	
	public static Specification searchProductByGallery(final boolean isGallery) {
		return new Specification() {
			
			@Override
			public Predicate toPredicate(Root item, CriteriaQuery query, CriteriaBuilder builder) {
				
				Predicate isGalleryPredicate = builder.or();
				if(isGallery == false) {
					isGalleryPredicate.getExpressions().add(builder.isTrue(item.get("isGalleryScore")));
				} 
				return isGalleryPredicate;
			}
		};
	}
	
	public static Specification searchProductByDirectioncut(final boolean isDirectioncut) {
		return new Specification() {
			
			@Override
			public Predicate toPredicate(Root item, CriteriaQuery query, CriteriaBuilder builder) {
				
				Predicate isDirectioncutPredicate = builder.or();
				if(isDirectioncut == false) {
					isDirectioncutPredicate.getExpressions().add(builder.isTrue(item.get("isDirectioncutScore")));
				} 
				return isDirectioncutPredicate;
			}
		};
	}
	
	public static Specification searchProductByEnterence(final boolean isEnterence) {
		return new Specification() {
			
			@Override
			public Predicate toPredicate(Root item, CriteriaQuery query, CriteriaBuilder builder) {
				
				Predicate isEnterencePredicate = builder.or();
				if(isEnterence == false) {
					isEnterencePredicate.getExpressions().add(builder.isTrue(item.get("isEnterenceScore")));
				} 
				return isEnterencePredicate;
			}
		};
	}
	
	public static Specification searchProductByWindow(final boolean isWindow) {
		return new Specification() {
			
			@Override
			public Predicate toPredicate(Root item, CriteriaQuery query, CriteriaBuilder builder) {
				
				Predicate isWindowPredicate = builder.or();
				if(isWindow == false) {
					isWindowPredicate.getExpressions().add(builder.isTrue(item.get("isWindowScore")));
				} 
				return isWindowPredicate;
			}
		};
	}
}
