package com.vishwakarma.vastu.service;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.vishwakarma.vastu.model.Product;
import com.vishwakarma.vastu.model.Role;
import com.vishwakarma.vastu.model.User;
import com.vishwakarma.vastu.model.specifications.ProductSpecification;
import com.vishwakarma.vastu.repository.ProductRepository;
import com.vishwakarma.vastu.utils.EncryptionUtil;

@Service
public class ProductService {

	private static final int PAGE_SIZE = 10;
	
	@Resource
	private ProductRepository productRepository;
	
	@Transactional
	public Product createproduct(Product product, User loggedInUser, boolean fetchEagerly) {
		
		if(null == product) {
			throw new VastuException("Invalid product");
		}
		product= productRepository.save(product);
		if(fetchEagerly){
			product = fetchEagerly(product);
		}
		return product;
	}
	
	@Transactional
	public Page<Product> getproducts(Integer pageNumber, User loggedInUser, boolean fetchEagerly) {
		
		Page<Product> page = null;
		PageRequest pageRequest = new PageRequest(pageNumber - 1, PAGE_SIZE, Direction.ASC, "id");
		page = productRepository.findAll(pageRequest);
		Iterator<Product> iterator = page.iterator();
		while (iterator.hasNext()) {
			Product product = (Product) iterator.next();
			if(fetchEagerly){
				product = fetchEagerly(product);
			}
			product = populateTransientFields(product, loggedInUser);
		}
		return page;
	}
		
	@Transactional
	public Page<Product> getproducts(Integer pageNumber, boolean fetchEagerly) {
		
		Page<Product> page = null;
		PageRequest pageRequest = new PageRequest(pageNumber - 1, PAGE_SIZE, Direction.ASC, "id");
		page = productRepository.findAll(pageRequest);
		Iterator<Product> iterator = page.iterator();
		while (iterator.hasNext()) {
			Product product = (Product) iterator.next();
			if(fetchEagerly){
				product = fetchEagerly(product);
			}
			product = populateTransientFields(product);
		}
		return page;
	}
	
	@Transactional
	public Page<Product> getproductsByCount(Integer pageNumber, Integer count, User loggedInUser, boolean fetchEagerly ) {
		
		Page<Product> page = null;
		PageRequest pageRequest = new PageRequest(pageNumber -1, PAGE_SIZE, Direction.ASC, "id");
		page = productRepository.findAll(ProductSpecification.searchProductByCount(count), pageRequest);
		Iterator<Product> iterator = page.iterator();
		while (iterator.hasNext()) {
			Product product = (Product) iterator.next();

			if(fetchEagerly){
				product = fetchEagerly(product);
			}
			
			product = populateTransientFields(product, loggedInUser);
		}
		return page;
	}
	
	@Transactional
	public Page<Product> getProductByLowScore(HashMap<String, Integer> hashMap, Integer pageNumber, boolean fetchEagerly) {
		
		Page<Product> page = null;
		PageRequest pageRequest = new PageRequest(pageNumber -1, PAGE_SIZE, Direction.ASC, "id");
		page = productRepository.findAll(ProductSpecification.searchProductByCount1(hashMap), pageRequest);
		Iterator<Product> iterator = page.iterator();
		while (iterator.hasNext()) {
			Product product = (Product) iterator.next();

			if(fetchEagerly){
				product = fetchEagerly(product);
			}
			
			product = populateTransientFields(product);
		}
		return page;
	}
	
	
	@Transactional
	public Page<Product> getKitchenProductsByScore(boolean isKitchen, Integer pageNumber, boolean fetchEagerly) {
		
		Page<Product> page = null;
		PageRequest pageRequest = new PageRequest(pageNumber -1, PAGE_SIZE, Direction.ASC, "id");
		page = productRepository.findAll(ProductSpecification.searchProductByKitchen(isKitchen), pageRequest);
		Iterator<Product> iterator = page.iterator();
		while (iterator.hasNext()) {
			Product product = (Product) iterator.next();

			if(fetchEagerly){
				product = fetchEagerly(product);
			}
			
			product = populateTransientFields(product);
		}
		return page;
	}
	
	
	@Transactional
	public Page<Product> getBedRoomProductsByScore(boolean isBedRoom, Integer pageNumber, boolean fetchEagerly) {
		
		Page<Product> page = null;
		PageRequest pageRequest = new PageRequest(pageNumber -1, PAGE_SIZE, Direction.ASC, "id");
		page = productRepository.findAll(ProductSpecification.searchProductByBedRoom(isBedRoom), pageRequest);
		Iterator<Product> iterator = page.iterator();
		while (iterator.hasNext()) {
			Product product = (Product) iterator.next();

			if(fetchEagerly){
				product = fetchEagerly(product);
			}
			
			product = populateTransientFields(product);
		}
		return page;
	}
	
	@Transactional
	public Page<Product> getEnterenceProductsByScore(boolean isEnterence, Integer pageNumber, boolean fetchEagerly) {
		
		Page<Product> page = null;
		PageRequest pageRequest = new PageRequest(pageNumber -1, PAGE_SIZE, Direction.ASC, "id");
		page = productRepository.findAll(ProductSpecification.searchProductByEnterence(isEnterence), pageRequest);
		Iterator<Product> iterator = page.iterator();
		while (iterator.hasNext()) {
			Product product = (Product) iterator.next();

			if(fetchEagerly){
				product = fetchEagerly(product);
			}
			product = populateTransientFields(product);
		}
		return page;
	}
	
	@Transactional
	public Integer getZeroScoreTiles(List<Integer> score) {
		
		int z=0;
		Iterator itr = score.iterator();
		while(itr.hasNext()) {
			z= (Integer) itr.next();
			if(z==0) {
				return z;
			}
		}
		return null;
	}
	
	@Transactional
	public List<Product> getproduct(){
		List<Product> products = productRepository.findAll();
		return products;
	}
	
	@Transactional
	public Page<Product> searchproduct(Integer pageNumber,String searchTerm, User loggedInUser,boolean fetchEagerly) {
		
		Page<Product> page = null;
		PageRequest pageRequest = new PageRequest(pageNumber - 1, PAGE_SIZE, Direction.ASC, "id");
		page = productRepository.findAll(ProductSpecification.search(searchTerm), pageRequest);
		Iterator<Product> iterator = page.iterator();
		while (iterator.hasNext()) {
			Product product = (Product) iterator.next();

			if(fetchEagerly){
				product = fetchEagerly(product);
			}
			
			product = populateTransientFields(product, loggedInUser);
		}
		return page;
	}
	
	
	@Transactional
	public Product getproduct(Long id, boolean doPostValidation, User loggedInUser, boolean populateTransientFields, boolean fetchEagerly) {
		
		if(null == id) {
			throw new VastuException("Invalid product id");
		}
		Product product = productRepository.findOne(id);
		if(doPostValidation && null == product) {
			throw new VastuException("product with this Id does not exists");
		} else if(null == product) {
			return product;
		}
		if(fetchEagerly){
			product = fetchEagerly(product);
		}
		if(populateTransientFields) {
			return populateTransientFields(product, loggedInUser);
		}
		return product;
	}
	
	@Transactional
	public Product getproductByCode(String code, boolean doPostValidation, User loggedInUser, boolean populateTransientFields, boolean fetchEagerly) {
		
		if(StringUtils.isBlank(code)) {
			throw new VastuException("Invalid product code");
		}
		Product product = productRepository.findByCode(code);
		if(doPostValidation && null == product) {
			throw new VastuException("product with this code does not exists");
		}
		if(fetchEagerly){
			product = fetchEagerly(product);
		}
		if(populateTransientFields) {
			return populateTransientFields(product, loggedInUser);
		}
		return product;
	}
	
	//Used for API Response
	
	@Transactional
	public Product getproductByCode(String code, boolean doPostValidation, boolean populateTransientFields, boolean fetchEagerly) {
		
		if(StringUtils.isBlank(code)) {
			throw new VastuException("Invalid product code");
		}
		Product product = productRepository.findByCode(code);
		if(doPostValidation && null == product) {
			throw new VastuException("product with this code does not exists");
		}
		if(fetchEagerly){
			product = fetchEagerly(product);
		}
		if(populateTransientFields) {
			return populateTransientFields(product);
		}
		return product;
	}
	
	
	
	@Transactional
	public Product getproductByName(String productName, boolean doPostValidation, User loggedInUser, boolean populateTransientFields, boolean fetchEagerly) {
		
		if(StringUtils.isBlank(productName)) {
			throw new VastuException("Invalid Product Name");
		}
		Product product = productRepository.findByProductName(productName);
		if (doPostValidation && product == null) {
			throw new VastuException("product with this name does not exist");
		} else if (product == null) {
			return product;
		}
		return product;
	}
	
	@Transactional
	public Product updateproduct(Long id, Product product, User loggedInUser, boolean populateTransientFields, boolean fetchEagerly) {
		
		if(null == id) {
			throw new VastuException("Invalid product id");
		}
		
		Product existingproduct = productRepository.findOne(id);
		
		if(null == existingproduct) {
			throw new VastuException("product with this Id does not exists");
		}
		
		existingproduct.setProductName(product.getProductName());
		existingproduct.setPrice(product.getPrice());
		existingproduct.setDescription(product.getDescription());
		
		if(null != existingproduct.getProductPicData()) {
			existingproduct.setProductPicData(product.getProductPicData());
			existingproduct.setFileName(product.getFileName());
		}
		
		existingproduct.setIsBathRoomScore(product.getIsBathRoomScore());
		existingproduct.setIsKitchenroomScore(product.getIsKitchenroomScore());
		existingproduct.setIsBedroomScore(product.getIsBedroomScore());
		existingproduct.setIsHallScore(product.getIsHallScore());
		existingproduct.setIsGalleryScore(product.getIsGalleryScore());
		existingproduct.setIsDirectioncutScore(product.getIsDirectioncutScore());
		existingproduct.setIsEnterenceScore(product.getIsEnterenceScore());
		existingproduct.setIsWindowScore(product.getIsWindowScore());
		
		existingproduct = productRepository.save(existingproduct);
		if(fetchEagerly){
			existingproduct = fetchEagerly(existingproduct);
		}
		if(populateTransientFields) {
			return populateTransientFields(existingproduct, loggedInUser);
		}	
		return existingproduct;
	}
	
	public void deleteproduct(Long id) {
		
		if(null == id) {
			throw new VastuException("Invalid product id");
		}
		Product product = productRepository.findOne(id);
		if(null == product) {
			throw new VastuException("product with this Id does not exists");
		}
		productRepository.delete(id);
	}
		
	private Product populateTransientFields(Product product, User loggedInUser) {
		
		if(null == product) {
			return product;
		}
		if(null == loggedInUser) {
			throw new VastuException("Invalid product");
		}
		if(loggedInUser.hasRole(Role.ROLE_SUPER_ADMIN)) {
			product.setIsEditAllowed(true);
			product.setIsDeleteAllowed(true);
		} else {
			product.setIsEditAllowed(false);
			product.setIsDeleteAllowed(false);
		}
		product.setCipher(EncryptionUtil.encode(product.getId()+""));
		return product;
	}
	
	// Used for API
	
	private Product populateTransientFields(Product product) {
		
		if(null == product) {
			return product;
		}
		product.setIsEditAllowed(false);
		product.setIsDeleteAllowed(false);
		product.setCipher(EncryptionUtil.encode(product.getId()+""));
		return product;
	}
	
	private Product fetchEagerly(Product product) {
		return product;
	}
	
	///Below methods for API 
	
	@Transactional
	public List<Product> getKitchenProductsByScore2(boolean isKitchen, Integer pageNumber, boolean fetchEagerly) {
		
		List<Product> products = new ArrayList<Product>();
		products = productRepository.findAll(ProductSpecification.searchProductByKitchen(isKitchen));
		for (Product product : products) {

			if(fetchEagerly){
				product = fetchEagerly(product);
			}
			product = populateTransientFields(product);
		}
		return products;
	}
	
	@Transactional
	public List<Product> getBedRoomProductsByScore2(boolean isBedRoom, Integer pageNumber, boolean fetchEagerly) {
		
		List<Product> products = new ArrayList<Product>();
		products = productRepository.findAll(ProductSpecification.searchProductByBedRoom(isBedRoom));
		for (Product product : products) {

			if(fetchEagerly){
				product = fetchEagerly(product);
			}
			product = populateTransientFields(product);
		}
		return products;
	}
	
	@Transactional
	public List<Product> getBathRoomProductsByScore2(boolean isBathRoom, Integer pageNumber, boolean fetchEagerly) {
		
		List<Product> products = new ArrayList<Product>();
		products = productRepository.findAll(ProductSpecification.searchProductByBathRoom(isBathRoom));
		for (Product product : products) {

			if(fetchEagerly){
				product = fetchEagerly(product);
			}
			product = populateTransientFields(product);
		}
		return products;
	}
	
	@Transactional
	public List<Product> getHallProductsByScore2(boolean isHall, Integer pageNumber, boolean fetchEagerly) {
		
		List<Product> products = new ArrayList<Product>();
		products = productRepository.findAll(ProductSpecification.searchProductByHall(isHall));
		for (Product product : products) {

			if(fetchEagerly){
				product = fetchEagerly(product);
			}
			product = populateTransientFields(product);
		}
		return products;
	}
	
	@Transactional
	public List<Product> getGalleryProductsByScore2(boolean isGallery, Integer pageNumber, boolean fetchEagerly) {
		
		List<Product> products = new ArrayList<Product>();
		products = productRepository.findAll(ProductSpecification.searchProductByGallery(isGallery));
		for (Product product : products) {

			if(fetchEagerly){
				product = fetchEagerly(product);
			}
			product = populateTransientFields(product);
		}
		return products;
	}
	
	@Transactional
	public List<Product> getDirectioncutProductsByScore2(boolean isDirectioncut, Integer pageNumber, boolean fetchEagerly) {
		
		List<Product> products = new ArrayList<Product>();
		products = productRepository.findAll(ProductSpecification.searchProductByDirectioncut(isDirectioncut));
		for (Product product : products) {

			if(fetchEagerly){
				product = fetchEagerly(product);
			}
			product = populateTransientFields(product);
		}
		return products;
	}
	
	@Transactional
	public List<Product> getEnterenceProductsByScore2(boolean isEnterence, Integer pageNumber, boolean fetchEagerly) {
		
		List<Product> products = new ArrayList<Product>();
		products = productRepository.findAll(ProductSpecification.searchProductByEnterence(isEnterence));
		for (Product product : products) {

			if(fetchEagerly){
				product = fetchEagerly(product);
			}
			product = populateTransientFields(product);
		}
		return products;
	}
	
	@Transactional
	public List<Product> getWindowProductsByScore2(boolean isWindow, Integer pageNumber, boolean fetchEagerly) {
		
		List<Product> products = new ArrayList<Product>();
		products = productRepository.findAll(ProductSpecification.searchProductByWindow(isWindow));
		for (Product product : products) {

			if(fetchEagerly){
				product = fetchEagerly(product);
			}
			product = populateTransientFields(product);
		}
		return products;
	}
	
}
