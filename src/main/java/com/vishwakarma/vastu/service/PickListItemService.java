package com.vishwakarma.vastu.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import com.vishwakarma.vastu.exception.VastuException;
import com.vishwakarma.vastu.model.PickListItem;
import com.vishwakarma.vastu.model.PickListItem.PickListType;
import com.vishwakarma.vastu.model.specifications.PickListItemSpecifications;
import com.vishwakarma.vastu.repository.PickListItemRepository;
import com.vishwakarma.vastu.utils.AppConstants;

@Service
public class PickListItemService {

	@Resource
	private PickListItemRepository pickListItemRepository;
	
	public List<PickListItem> getAllPickListItems() {
		return pickListItemRepository.findAll();
	}
	
	public Page<PickListItem> getPickListItems(Integer pageNumber) {
		PageRequest pageRequest = new PageRequest(pageNumber - 1, AppConstants.PAGE_SIZE, Sort.Direction.DESC, "id");
		List<String> listTypes = new ArrayList<String>();
		listTypes.add(PickListType.COUNTRY.toString());
		return pickListItemRepository.fineByNotIn(listTypes, pageRequest);
	}
	
	public PickListItem getPickListItem(Long id) {
		PickListItem pickListItem = pickListItemRepository.findOne(id);
		return pickListItem;
	}
	
	public PickListItem getPickListItemByItemValue(String itemValue) {
		return pickListItemRepository.findByItemValue(itemValue);
	}
	
	public List<PickListItem> getPickListItemByListType(String listType) {
		return pickListItemRepository.findByListType(listType);
	}
	
	@SuppressWarnings("unchecked")
	public Page<PickListItem> search(Integer pageNumber, String searchTerm) {
		PageRequest pageRequest = new PageRequest(pageNumber - 1, AppConstants.PAGE_SIZE, Sort.Direction.DESC, "id");
		return pickListItemRepository.findAll(Specifications.where(PickListItemSpecifications.search(searchTerm)), pageRequest);
	}
	
	public PickListItem addPickListItem(PickListItem pickListItem) {
		if(pickListItem == null) {
			throw new VastuException("Invalid PickListItem Obj");
		}
		pickListItem.setItemValue(pickListItem.getDisplayValue().replace(" ", ""));
		PickListItem existingPickListItem = pickListItemRepository.findByItemValue(pickListItem.getItemValue());
		if(existingPickListItem != null) {
			throw new VastuException("PickListItem with value - " + pickListItem.getDisplayValue() + " already exists");
		}
		return pickListItemRepository.save(pickListItem);
	}
	
	public PickListItem updatePickListItem(Long pickListItemId, PickListItem pickListItem) {
		if(pickListItemId == null) {
			throw new VastuException("Invalid PickListItem Id");
		}
		if(pickListItem == null) {
			throw new VastuException("Invalid PickListItem Obj");
		}
		PickListItem existingPickListItem = pickListItemRepository.findOne(pickListItemId);
		if(existingPickListItem == null) {
			throw new VastuException("PickListItem with Id - " + pickListItemId + " does not exists");
		}
		existingPickListItem.setDisplayValue(pickListItem.getDisplayValue());
		existingPickListItem.setItemValue(pickListItem.getDisplayValue().replace(" ", ""));
		existingPickListItem.setListType(pickListItem.getListType());
		return pickListItemRepository.save(existingPickListItem);
	}
	
}
