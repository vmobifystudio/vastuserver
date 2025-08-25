package com.vishwakarma.vastu.property.editor;

import java.beans.PropertyEditorSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vishwakarma.vastu.model.LineItem;
import com.vishwakarma.vastu.model.Product;
import com.vishwakarma.vastu.repository.LineItemRepository;

public class LineItemPropertyEditor extends PropertyEditorSupport {

	LineItemRepository lineItemRepository;
	
	Logger log = LoggerFactory.getLogger(LineItemPropertyEditor.class);
	
	public LineItemPropertyEditor(LineItemRepository lineItemRepository) {
		this.lineItemRepository =  lineItemRepository;
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
				LineItem  lineItem = lineItemRepository.findOne(id);
				
				if(null != lineItem) {
					super.setValue(lineItem);
				} else {
					log.error("Binding Error:Can not find lineItem with Id - " + text);
					throw new IllegalArgumentException("Binding Error:Can not find lineItem with id - " + text);
				}
			} catch (NumberFormatException e) {
				log.error("Binding Error:Invalid Id - " + text);
				throw new IllegalArgumentException("Binding Error:Can not find lineItem with id - " + text);
			}
		}
}
