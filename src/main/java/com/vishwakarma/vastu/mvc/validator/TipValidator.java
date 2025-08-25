package com.vishwakarma.vastu.mvc.validator;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.vishwakarma.vastu.model.Tip;
import com.vishwakarma.vastu.service.TipService;
import com.vishwakarma.vastu.service.UserService;
@Component
public class TipValidator implements Validator {

	@Resource(name = "mvcValidator")
	private Validator validator;
	
	@Resource
	private UserService userService;
	
	@Resource
	private TipService tipService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Tip.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Tip tip = (Tip) target;
		if(StringUtils.isNotBlank(tip.getTipTitle()) && !tip.isUpdateOperation()) {
			Tip existingTip = tipService.getTipByTitle(tip.getTipTitle(), false , userService.getLoggedInUser(), false , false);
			if(existingTip != null) {
				errors.rejectValue("target", "Tip with this title already exist", "Tip with this title already exist");
			}
		}
	}
}
