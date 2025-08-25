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
import com.vishwakarma.vastu.model.OrderProduct;
import com.vishwakarma.vastu.model.Role;
import com.vishwakarma.vastu.model.User;
import com.vishwakarma.vastu.model.specifications.OrderProductSpecification;
import com.vishwakarma.vastu.repository.OrderProductRepository;
import com.vishwakarma.vastu.utils.EncryptionUtil;

@Service
public class OrderProductService {

private static final int PAGE_SIZE = 10;
	
	@Resource
	private  OrderProductRepository orderProductRepository;
	
	@Resource
	private ProductService productService;
	
	@Transactional
	public OrderProduct createorderProduct(OrderProduct orderProduct, User loggedInUser, boolean fetchEagerly) {
		
		if(null == orderProduct) {
			throw new VastuException("Invalid orderProduct");
		}
		orderProduct= orderProductRepository.save(orderProduct);
		if(fetchEagerly){
			orderProduct = fetchEagerly(orderProduct);
		}
		return orderProduct;
	}
	
	//Used for API
	
	@Transactional
	public OrderProduct createorderProduct(OrderProduct orderProduct, boolean fetchEagerly) {
		
		if(null == orderProduct) {
			throw new VastuException("Invalid orderProduct");
		}
		orderProduct= orderProductRepository.save(orderProduct);
		if(fetchEagerly){
			orderProduct = fetchEagerly(orderProduct);
		}
		return orderProduct;
	}
	
	@Transactional
	public Page<OrderProduct> getorderProducts(Integer pageNumber, User loggedInUser, boolean fetchEagerly) {
		
		Page<OrderProduct> page = null;
		PageRequest pageRequest = new PageRequest(pageNumber - 1, PAGE_SIZE, Direction.ASC, "id");
		page = orderProductRepository.findAll(pageRequest);
		Iterator<OrderProduct> iterator = page.iterator();
		while (iterator.hasNext()) {
			OrderProduct orderProduct = (OrderProduct) iterator.next();
			if(fetchEagerly){
				Object object = productService.getproduct().size();
			}
			orderProduct = populateTransientFields(orderProduct, loggedInUser);
		}
		return page;
	}
	
	@Transactional
	public List<OrderProduct> getorderProduct(){
		List<OrderProduct> orderProducts = orderProductRepository.findAll();
		return orderProducts;
	}
	
	@Transactional
	public Page<OrderProduct> searchorderProducts(Integer pageNumber,String searchTerm, User loggedInUser,boolean fetchEagerly) {
		
		Page<OrderProduct> page = null;
		PageRequest pageRequest = new PageRequest(pageNumber - 1, PAGE_SIZE, Direction.ASC, "id");
		page = orderProductRepository.findAll(OrderProductSpecification.search(searchTerm), pageRequest);
		Iterator<OrderProduct> iterator = page.iterator();
		while (iterator.hasNext()) {
			OrderProduct orderProduct = (OrderProduct) iterator.next();

			if(fetchEagerly){
				orderProduct = fetchEagerly(orderProduct);
			}
			
			orderProduct = populateTransientFields(orderProduct, loggedInUser);
		}
		return page;
	}
	
	
	@Transactional
	public OrderProduct getorderProduct(Long id, boolean doPostValidation, User loggedInUser, boolean populateTransientFields, boolean fetchEagerly) {
		
		if(null == id) {
			throw new VastuException("Invalid orderProduct id");
		}
		OrderProduct orderProduct = orderProductRepository.findOne(id);
		if(doPostValidation && null == orderProduct) {
			throw new VastuException("orderProduct with this Id does not exists");
		} else if(null == orderProduct) {
			return orderProduct;
		}
		if(fetchEagerly){
			orderProduct = fetchEagerly(orderProduct);
		}
		if(populateTransientFields) {
			return populateTransientFields(orderProduct, loggedInUser);
		}
		return orderProduct;
	}
	
	@Transactional
	public OrderProduct getorderProductByCode(String code, boolean doPostValidation, User loggedInUser, boolean populateTransientFields, boolean fetchEagerly) {
		
		if(StringUtils.isBlank(code)) {
			throw new VastuException("Invalid orderProduct code");
		}
		OrderProduct orderProduct = orderProductRepository.findByCode(code);
		if(doPostValidation && null == orderProduct) {
			throw new VastuException("orderProduct with this code does not exists");
		}
		if(fetchEagerly){
			orderProduct = fetchEagerly(orderProduct);
		}
		if(populateTransientFields) {
			return populateTransientFields(orderProduct, loggedInUser);
		}
		return orderProduct;
	}
	
	/*@Transactional
	public OrderProduct getorderProductByorderProductId(String orderProductId, boolean doPostValidation, User loggedInUser, boolean populateTransientFields, boolean fetchEagerly) {
		
		if(StringUtils.isBlank(orderProductId)) {
			throw new VastuException("Invalid orderProduct Id");
		}
		OrderProduct orderProduct = orderProductRepository.findByorderProductId(orderProductId);
		if (doPostValidation && orderProduct == null) {
			throw new VastuException("orderProduct with this name does not exist");
		} else if (orderProduct == null) {
			return orderProduct;
		}
		return orderProduct;
	}*/
	
	@Transactional
	public OrderProduct updateorderProduct(Long id, OrderProduct orderProduct, User loggedInUser, boolean populateTransientFields, boolean fetchEagerly) {
		
		if(null == id) {
			throw new VastuException("Invalid orderProduct id");
		}
		
		OrderProduct existingorderProduct = orderProductRepository.findOne(id);
		
		if(null == existingorderProduct) {
			throw new VastuException("orderProduct with this Id does not exists");
		}
		
		existingorderProduct.setName(orderProduct.getName());
		existingorderProduct.setAddress(orderProduct.getAddress());
		existingorderProduct.setPin(orderProduct.getPin());
		existingorderProduct.setMobileNumber(orderProduct.getMobileNumber());
		
		existingorderProduct.getLineItems().clear();
		existingorderProduct.getLineItems().addAll(orderProduct.getLineItems());
		
		existingorderProduct = orderProductRepository.save(existingorderProduct);
		if(fetchEagerly){
			existingorderProduct = fetchEagerly(existingorderProduct);
		}
		if(populateTransientFields) {
			return populateTransientFields(existingorderProduct, loggedInUser);
		}	
		return existingorderProduct;
	}
	
	public void deleteorderProduct(Long id) {
		
		if(null == id) {
			throw new VastuException("Invalid orderProduct id");
		}
		OrderProduct orderProduct = orderProductRepository.findOne(id);
		if(null == orderProduct) {
			throw new VastuException("orderProduct with this Id does not exists");
		}
		orderProductRepository.delete(id);
	}
		
	private OrderProduct populateTransientFields(OrderProduct orderProduct, User loggedInUser) {
		
		if(null == orderProduct) {
			return orderProduct;
		}
		if(null == loggedInUser) {
			throw new VastuException("Invalid orderProduct");
		}
		if(loggedInUser.hasRole(Role.ROLE_SUPER_ADMIN)) {
			orderProduct.setIsEditAllowed(true);
			orderProduct.setIsDeleteAllowed(true);
		} else {
			orderProduct.setIsEditAllowed(false);
			orderProduct.setIsDeleteAllowed(false);
		}
		orderProduct.setCipher(EncryptionUtil.encode(orderProduct.getId()+""));
		return orderProduct;
	}
	
	private OrderProduct fetchEagerly(OrderProduct orderProduct) {
		
		return orderProduct;
		
	}
}
