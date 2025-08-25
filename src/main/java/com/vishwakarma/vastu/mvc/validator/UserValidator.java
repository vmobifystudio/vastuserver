package com.vishwakarma.vastu.mvc.validator;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.vishwakarma.vastu.model.User;
import com.vishwakarma.vastu.service.UserService;

/**
 * @author Vishal
 *
 */
@Component
public class UserValidator implements Validator{

	@Resource(name="mvcValidator")
	private Validator validator;
	
	@Resource
	private UserService userService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		
		errors = validateCommonFields(user, errors);
		
		if(StringUtils.isBlank(user.getPassword())){
			errors.rejectValue("password", "Please Enter valid Password", "Please Enter valid Password");
		}
		validator.validate(user, errors);
	}
	
	public Errors validateCommonFields(User user, Errors errors) {

		if(StringUtils.isBlank(user.getEmail())) {
			errors.rejectValue("email", "Invalid email", "Invalid email");
		}

		if(StringUtils.isBlank(user.getMobileNumber())) {
			errors.rejectValue("mobileNumber", "Invalid mobile number", "Invalid mobile number");
		}
		
		if(!user.isUpdateOperation()) {
			if(StringUtils.isNotBlank(user.getEmail())) {
				User existingUser = userService.getUserByEmail(user.getEmail(), false);
				if(null != existingUser) {
					errors.rejectValue("email", "User with this email already exists", "User with this email already exists");
				}
			}
		}
		return errors;
	}
}
