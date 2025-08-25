package com.vishwakarma.vastu.service;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vishwakarma.vastu.exception.VastuException;
import com.vishwakarma.vastu.model.LineItem;
import com.vishwakarma.vastu.model.LineItem;
import com.vishwakarma.vastu.model.Role;
import com.vishwakarma.vastu.model.User;
import com.vishwakarma.vastu.repository.LineItemRepository;
import com.vishwakarma.vastu.utils.EncryptionUtil;

@Service
public class LineItemService {

	private static final int PAGE_SIZE = 10;
	@Resource
	private LineItemRepository lineItemRepository;
	
	@Resource
	private UserService userService;
	
	@Transactional
	public LineItem createLineItem(LineItem lineItem, User loggedInUser, boolean fetchEagerly) {
		
		if(null == lineItem) {
			throw new VastuException("Invalid LineItem");
		}
		lineItem= lineItemRepository.save(lineItem);
		if(fetchEagerly){
			lineItem = fetchEagerly(lineItem);
		}
		return lineItem;
	}
	
	@Transactional
	public LineItem getLineItemByCode(String code, boolean doPostValidation, User loggedInUser, boolean populateTransientFields, boolean fetchEagerly) {
		
		if(StringUtils.isBlank(code)) {
			throw new VastuException("Invalid LineItem code");
		}
		LineItem  lineItem = lineItemRepository.findByCode(code);
		if(doPostValidation && null == lineItem) {
			throw new VastuException("LineItem with this code does not exists");
		}
		if(fetchEagerly){
			lineItem = fetchEagerly(lineItem);
		}
		if(populateTransientFields) {
			return populateTransientFields(lineItem, loggedInUser);
		}
		return lineItem;
	}
	
	@Transactional
	public Page<LineItem> getLineItems(Integer pageNumber, User loggedInUser, boolean fetchEagerly) {
		
		Page<LineItem> page = null;
		PageRequest pageRequest = new PageRequest(pageNumber - 1, PAGE_SIZE, Direction.ASC, "id");
		page = lineItemRepository.findAll(pageRequest);
		Iterator<LineItem> iterator = page.iterator();
		while (iterator.hasNext()) {
			LineItem lineItem = (LineItem) iterator.next();
			if(fetchEagerly){
				lineItem = fetchEagerly(lineItem);
				
			}
			lineItem = populateTransientFields(lineItem, loggedInUser);
		}
		return page;
	}
	
	@Transactional
	public List<LineItem> getLineItem(){
		List<LineItem> LineItems = lineItemRepository.findAll();
		return LineItems;
	}
	
	/*@Transactional
	public Page<LineItem> searchLineItems(Integer pageNumber,String searchTerm, User loggedInUser,boolean fetchEagerly) {
		
		Page<LineItem> page = null;
		PageRequest pageRequest = new PageRequest(pageNumber - 1, PAGE_SIZE, Direction.ASC, "id");
		page = LineItemRepository.findAll(LineItemSpecification.search(searchTerm), pageRequest);
		Iterator<LineItem> iterator = page.iterator();
		while (iterator.hasNext()) {
			LineItem lineItem = (LineItem) iterator.next();

			if(fetchEagerly){
				lineItem = fetchEagerly(lineItem);
				
			}
			
			lineItem = populateTransientFields(lineItem, loggedInUser);
		}
		return page;
	}*/
	
	
	@Transactional
	public LineItem getLineItem(Long id, boolean doPostValidation, User loggedInUser, boolean populateTransientFields, boolean fetchEagerly) {
		
		if(null == id) {
			throw new VastuException("Invalid LineItem id");
		}
		LineItem lineItem = lineItemRepository.findOne(id);
		if(doPostValidation && null == lineItem) {
			throw new VastuException("LineItem with this Id does not exists");
		} else if(null == lineItem) {
			return lineItem;
		}
		if(fetchEagerly){
			lineItem = fetchEagerly(lineItem);
		}
		if(populateTransientFields) {
			return populateTransientFields(lineItem, loggedInUser);
		}
		return lineItem;
	}
	
	@Transactional
	public LineItem updateLineItem(Long id, LineItem lineItem, User loggedInUser, boolean populateTransientFields, boolean fetchEagerly) {
		
		if(null == id) {
			throw new VastuException("Invalid LineItem id");
		}
		
		LineItem existingLineItem = lineItemRepository.findOne(id);
		
		if(null == existingLineItem) {
			throw new VastuException("LineItem with this Id does not exists");
		}
		
		existingLineItem.setCount(lineItem.getCount());
		existingLineItem.setProduct(lineItem.getProduct());
		existingLineItem = lineItemRepository.save(existingLineItem);
		if(fetchEagerly){
			existingLineItem = fetchEagerly(existingLineItem);
		}
		if(populateTransientFields) {
			return populateTransientFields(existingLineItem, loggedInUser);
		}	
		return existingLineItem;
	}
	
	public void deleteLineItem(Long id) {
		
		if(null == id) {
			throw new VastuException("Invalid LineItem id");
		}
		LineItem LineItem = lineItemRepository.findOne(id);
		if(null == LineItem) {
			throw new VastuException("LineItem with this Id does not exists");
		}
		lineItemRepository.delete(id);
	}
	
	private LineItem populateTransientFields(LineItem lineItem, User loggedInUser) {
		
		if(null == lineItem) {
			return lineItem;
		}
		if(null == loggedInUser) {
			throw new VastuException("Invalid LineItem");
		}
		if(loggedInUser.hasRole(Role.ROLE_SUPER_ADMIN)) {
			lineItem.setIsEditAllowed(true);
			lineItem.setIsDeleteAllowed(true);
		} else {
			lineItem.setIsEditAllowed(false);
			lineItem.setIsDeleteAllowed(false);
		}
		lineItem.setCipher(EncryptionUtil.encode(lineItem.getId()+""));
		return lineItem;
	}
	
	private LineItem fetchEagerly(LineItem lineItem) {
		
		return lineItem;
	}
	
}
