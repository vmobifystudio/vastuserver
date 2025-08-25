package com.vishwakarma.vastu.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.vishwakarma.vastu.model.PickListItem;
import com.vishwakarma.vastu.repository.PickListItemRepository;

@Component
public class PickListHelper {

	private Map<String, List<PickListItem>> pickListData;
	
	@Resource 
	private PickListItemRepository pickListItemRepository;
	
	@PostConstruct
	public void loadPickListData() {
		List<PickListItem> allItems = pickListItemRepository.findAll();
		pickListData = new HashMap<String, List<PickListItem>>();
		for (PickListItem item : allItems) {
			if (pickListData.get(item.getListType())==null) {
				pickListData.put(item.getListType(), new ArrayList<PickListItem>());
			}
			pickListData.get(item.getListType()).add(item);
		}
	}

	/*public List<PickListItem> getPickListItemsForType(String type) {
		return pickListData.get(type);
	}*/
}
