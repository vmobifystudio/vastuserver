package com.vishwakarma.vastu.mvc.validator;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.vishwakarma.vastu.model.Product;
import com.vishwakarma.vastu.service.ProductService;
import com.vishwakarma.vastu.service.UserService;
@Component
public class ProductValidatior implements Validator {
	
	@Resource(name="mvcValidator")
	private Validator validator;
	
	@Resource
	private ProductService productService;
	
	@Resource
	private UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Product.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		Product product = (Product) target;
		if(!StringUtils.isNotBlank(product.getProductName()) || !product.isUpdateOperation()) {
			Product existingProduct = productService.getproductByName(product.getProductName(), false, userService.getLoggedInUser(), false, false);
			if(existingProduct != null) {
				errors.rejectValue("productName", "Product with this name already exist", "Product with this name already exist");
			}
		}
	}

}
