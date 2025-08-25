package com.vishwakarma.vastu.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vishwakarma.vastu.model.PickListItem;

@Repository
public interface PickListItemRepository extends CustomRepository<PickListItem, Long> {

	List<PickListItem> findByListType (String type);
	
	public PickListItem findByItemValue(String itemValue);
	
	@Query("select distinct p from PickListItem p where p.listType not in :listTypes")
	Page<PickListItem> fineByNotIn(@Param("listTypes") List<String> listTypes, Pageable page); 
}

