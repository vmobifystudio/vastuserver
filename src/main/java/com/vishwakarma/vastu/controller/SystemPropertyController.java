package com.vishwakarma.vastu.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.vishwakarma.vastu.model.SystemProperty;
import com.vishwakarma.vastu.repository.SystemPropertyRepository;
import com.vishwakarma.vastu.utils.SystemPropertyHelper;

@Controller
@SessionAttributes({"id"})
public class SystemPropertyController {

	@Resource
	private SystemPropertyRepository systemPropertyRepository;
	
	@Resource
	private SystemPropertyHelper systemPropertyHelper;
	
	private final static Logger log = LoggerFactory.getLogger(SystemPropertyController.class);
			
	@RequestMapping("/systemproperty/list")
	public String systemPropertyList(final Model model,
			@RequestParam(defaultValue="1") Integer pageNumber,
			@RequestParam(required = false) String message) {
		model.addAttribute("message", message);
		
		Page<SystemProperty> page = systemPropertyHelper.getSystemProperties(pageNumber);
	    int current = page.getNumber() + 1;
	    int begin = Math.max(1, current - 5);
	    int end = Math.min(begin + 10, page.getTotalPages());

	    model.addAttribute("page", page);
	    model.addAttribute("beginIndex", begin);
	    model.addAttribute("endIndex", end);
	    model.addAttribute("currentIndex", current);
	    model.addAttribute("properties", page.getContent());
		
		return "systemproperty/list";
	}
	
	@RequestMapping(value = "/systemproperty/add", method = RequestMethod.GET)
	public String systemPropertyAdd(Model model, @RequestParam(required=false) String retUrl) {
		model.addAttribute("property", new SystemProperty());
		if (!StringUtils.isEmpty(retUrl)) {
			model.addAttribute("retUrl", retUrl);
		}
		return "systemproperty/add";
	}
	
	@RequestMapping(value = { "/systemproperty/add" }, method = RequestMethod.POST)
	public String submitSystemProperty(@Valid SystemProperty systemproperty, BindingResult formBinding) {
		if (formBinding.hasErrors()) {
			return "systemproperty/add";
		}
		systemPropertyRepository.save(systemproperty);
		String message = "Added Successfully!";
		return "redirect:/systemproperty/list?message=" + message;
	}
	
	@RequestMapping(value = {"/systemproperty/{systempropertyId}","/show/systemproperty/{systempropertyId}"}, method = RequestMethod.GET)
	public String updateSystemProperty(@PathVariable Long systempropertyId,Model model,@RequestParam(required=false) String readOnly) {
		
		SystemProperty property = systemPropertyRepository.findOne(systempropertyId);
		log.info("Property Id --- " + systempropertyId + " --- " + systempropertyId);
		model.addAttribute("readOnly", readOnly);
		model.addAttribute("property", property);
		model.addAttribute("id", property.getId());
		return "systemproperty/update";
	}
	
	@RequestMapping(value = { "/systemproperty/update" }, method = RequestMethod.POST)
	public String updateSystemProperty(@Valid SystemProperty property, BindingResult formBinding,
			Map<String, Object> model) {
		
		property.setId(Long.parseLong(model.get("id").toString()));
		systemPropertyRepository.save(property);
		model.remove("id");
		model.remove("version");
		String message = "Updated Successfully!";
		return "redirect:/systemproperty/list?message=" + message;
	}
	
	@RequestMapping(value = "/systemproperty/delete/{systempropertyId}")
	public String deleteStatus(@PathVariable Long systempropertyId) {
		log.info("Property Id --- " + systempropertyId + " --- " + systempropertyId);
		systemPropertyRepository.delete(systempropertyId);
		String message = "Deleted Successfully!";
		return "redirect:/systemproperty/list?message=" + message;
	}

}
