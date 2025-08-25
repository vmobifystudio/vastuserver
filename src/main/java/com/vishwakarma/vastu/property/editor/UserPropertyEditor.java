package com.vishwakarma.vastu.property.editor;

import java.beans.PropertyEditorSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vishwakarma.vastu.model.User;
import com.vishwakarma.vastu.repository.UserRepository;

public class UserPropertyEditor extends PropertyEditorSupport{

	private UserRepository userRepository;
	
	private Logger log = LoggerFactory.getLogger(UserPropertyEditor.class);
	
	public UserPropertyEditor(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public String getAsText() {
		User obj = (User) getValue();
		if(obj == null) {
			return "";
		}
		return obj.toString();
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		try {
			Long id = Long.parseLong(text);
			User user = userRepository.findOne(id);
			
			if(null != user) {

				super.setValue(user);
			} else {
				log.error("Binding Error:Can not find User with Id - " + text);
				throw new IllegalArgumentException("Binding Error:Can not find User with id - " + text);
			}
		} catch (NumberFormatException e) {
			log.error("Binding Error:Invalid Id - " + text);
			throw new IllegalArgumentException("Binding Error:Can not find User with id - " + text);
		}
	}

	
}
