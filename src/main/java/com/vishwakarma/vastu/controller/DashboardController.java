package com.vishwakarma.vastu.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vishwakarma.vastu.model.SystemProperty;
import com.vishwakarma.vastu.service.CommonService;
import com.vishwakarma.vastu.service.PickListItemService;
import com.vishwakarma.vastu.service.UserService;
import com.vishwakarma.vastu.utils.SystemPropertyHelper;

@Controller
public class DashboardController {

	@Resource 
	private CommonService commonService;
	
	@Autowired 
	private SystemPropertyHelper systemPropertyHelper;
	
	@Resource
	private PickListItemService pickListItemService;
	
	@Resource
	private UserService userService;
	
	@RequestMapping("/home")
	public String dashboard(final Model model, 
							HttpSession httpSession,
							@RequestParam(required = false) String message,
							HttpServletResponse response) {
		// Setup model for dashboard
		
		String orgName = systemPropertyHelper.getSystemProperty(SystemProperty.ORG_NAME, null);
		if (null!=orgName) {
			model.addAttribute("orgName", orgName);
			httpSession.setAttribute("orgName", orgName);
		}
		return "/home";
	}
}
