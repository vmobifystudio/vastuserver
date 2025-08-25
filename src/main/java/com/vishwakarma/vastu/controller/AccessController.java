package com.vishwakarma.vastu.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vishwakarma.vastu.api.ApiResponse;
import com.vishwakarma.vastu.dto.UserDTO;
import com.vishwakarma.vastu.model.UserInfo;
import com.vishwakarma.vastu.service.AccessService;
import com.vishwakarma.vastu.service.UserService;

@Controller
public class AccessController {

	@Resource
	private AccessService accessService;
	
	@Resource
	private UserService userService;
	
	@RequestMapping("/login")
	public String login(Model model, @RequestParam(required=false) String message, HttpServletResponse response) {
		model.addAttribute("message", message);
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache"); 
		response.setDateHeader("Expires", 0); 
		return "access/login";
	}
	
	@RequestMapping(value = "/denied")
 	public String denied() {
		return "access/denied";
	}
	
	@RequestMapping(value = "/login/failure")
 	public String loginFailure() {
		String message = "Login Failure!";
		return "redirect:/login?message="+message;
	}
	
	@RequestMapping(value = "/logout/success")
 	public String logoutSuccess() {
		String message = "Logout Success!";
		return "redirect:/login?message="+message;
	}
	
	@RequestMapping(value="/access/forgotPassword", method=RequestMethod.GET)
	public String recoverPassword(){
		return "/access/forgotpassword";
	}

	@RequestMapping(value="/access/forgotPassword", method=RequestMethod.POST)
	public String recoverPassword(@RequestParam(required=true) String email) {
		boolean result;
		String message = "";
		result = accessService.recoverUserPassword(email);
		if(result) {
			message = "Successfully changed password, please check your email!";
			return "redirect:/login?message="+message;
		}
		message = "Unable to change the password, please try later!";
		return "redirect:/login?message="+message;
	}
	
	@RequestMapping(value="/access/changePassword", method=RequestMethod.GET)
	public String changePassword() {
		return "/changePassword";
	}
	
	@RequestMapping(value="/access/changePassword", method=RequestMethod.POST)
	public String changePassword(@RequestParam(required=true) String oldPassword, @RequestParam(required=true) String newPassword) {
		
		String message = "";
		if(null == oldPassword || StringUtils.isBlank(oldPassword)) {
			message = "Incorrect current password!";
			return "redirect:/changePassword?message=" + message;
		}
		if(null == newPassword || StringUtils.isBlank(newPassword)) {
			message = "Invalid new passwords!";
			return "redirect:/changePassword?message=" + message;
		}
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		boolean isCorrectPass = userService.checkPassword(userInfo.getId(), oldPassword);
		if(isCorrectPass) {
			try {
				userService.changePassword(newPassword);
				message = "Password changed successfully";
				return "/home?message=" + message;
			} catch(Exception e) {
				e.printStackTrace();
				message = "Unable to change password..Please try again later";
			}
		} else {
			message = "Incorrect old password";
		}
		return "redirect:/changePassword?message=" + message;
	}
	
	/*
	 * 
	 * REST API Methods
	 * 
	 */
	
	/**
	 * Actual login is done by spring, we will populate the response here
	 * @return
	 */
	@RequestMapping(value="/restapi/login", method=RequestMethod.POST)
	@ResponseBody
	public ApiResponse apiLogin() {
		ApiResponse apiResponse = new ApiResponse(true);
		UserDTO userDTO = new UserDTO(userService.getLoggedInUser());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user", userDTO);
		apiResponse.setData(map);
		return apiResponse;
	}
	
	@RequestMapping(value = "/restapi/denied")
 	@ResponseBody 
 	public ApiResponse apiAccessDenied() {
		return new ApiResponse(false).setError("Invalid Email/Password or User is not enabled", "AccessDenied");
	}
}