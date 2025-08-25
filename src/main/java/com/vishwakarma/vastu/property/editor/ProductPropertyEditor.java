package com.vishwakarma.vastu.property.editor;

import java.beans.PropertyEditorSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vishwakarma.vastu.model.Product;
import com.vishwakarma.vastu.repository.ProductRepository;

public class ProductPropertyEditor extends PropertyEditorSupport {

	ProductRepository productRepository;
	
	Logger log = LoggerFactory.getLogger(ProductPropertyEditor.class);
	
	public ProductPropertyEditor(ProductRepository productRepository) {
		this.productRepository =  productRepository;
	}
		@Override
		public String getAsText() {

			Product obj = (Product) getValue();
			if(obj == null) {
				return "";
			}
			return obj.toString();
		}
		
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
		
			try {
				Long id = Long.parseLong(text);
				Product product = productRepository.findOne(id);
				
				if(null != product) {
					super.setValue(product);
				} else {
					log.error("Binding Error:Can not find product with Id - " + text);
					throw new IllegalArgumentException("Binding Error:Can not find product with id - " + text);
				}
			} catch (NumberFormatException e) {
				log.error("Binding Error:Invalid Id - " + text);
				throw new IllegalArgumentException("Binding Error:Can not find product with id - " + text);
			}
		}
}
