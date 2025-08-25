package com.vishwakarma.vastu.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.CharacterEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.vishwakarma.vastu.model.User;
import com.vishwakarma.vastu.mvc.validator.UserValidator;
import com.vishwakarma.vastu.service.UserService;
import com.vishwakarma.vastu.utils.DateHelper;
import com.vishwakarma.vastu.utils.EncryptionUtil;
import com.vishwakarma.vastu.utils.RoleCache;

/**
 * @author Vishal
 *
 */
@Controller
@SessionAttributes(value={"id", "version", "retUrl", "retPage"})
public class UserController {
	
	@Resource
	private UserValidator userValidator;
	
	@Resource
	private UserService userService;
	
	@Resource
	private RoleCache roleCache;
	
	@InitBinder(value="user")
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields("id", "version");
		CustomDateEditor customDateEditor = new CustomDateEditor(new SimpleDateFormat(DateHelper.DATE_FORMAT), false);
		binder.registerCustomEditor(Date.class, customDateEditor);
		binder.registerCustomEditor(Character.class, new CharacterEditor(false));
		binder.setValidator(userValidator);
	}
	
	private Model populateModelForAdd(Model model, User user) {
		model.addAttribute("user", user);
		model.addAttribute("today", DateHelper.getFormattedDate(new Date()));
		return model;
	}
	
	@RequestMapping(value="/user/add")
	public String addUser(Model model) {
		model = populateModelForAdd(model, new User());
		return "/user/add";
	}

	@RequestMapping(value="/user/add", method=RequestMethod.POST)
	public String userAdd(User user, BindingResult formBinding, Model model) {
		
		userValidator.validate(user, formBinding);
		if(formBinding.hasErrors()) {
			model = populateModelForAdd(model, user);
			return "/user/add";
		}
		User loggedInUser = userService.getLoggedInUser();
		userService.createUser(user, loggedInUser, false);
		String message = "User added successfully";
		return "redirect:/user/list?message="+message;
	}

	@RequestMapping(value="/user/list")
	public String getUserList(Model model,
										@RequestParam(defaultValue="1") Integer pageNumber,
										@RequestParam(required=false) String message,
										@RequestParam(required=false) String searchTerm) {
		model.addAttribute("message", message);
		Page<User> page = null;
		User user = userService.getLoggedInUser();
		if(StringUtils.isNotBlank(searchTerm)) {
			page = userService.searchUsers(pageNumber, searchTerm, user);
		} else {
			page = userService.getUsers(pageNumber, user);
		}
		int currentIndex = page.getNumber() + 1;
		int beginIndex = Math.max(1, currentIndex - 5);
		int endIndex = Math.min(beginIndex + 10, page.getTotalPages());
		
		model.addAttribute("page", page);
		model.addAttribute("currentIndex", currentIndex);
		model.addAttribute("beginIndex", beginIndex);
		model.addAttribute("endIndex", endIndex);
		model.addAttribute("searchTerm", searchTerm);
		model.addAttribute("users", page.getContent());
		
		return "/user/list";
	}
	
	@RequestMapping(value="/user/show/{cipher}")
	public String showUser(@PathVariable String cipher, Model model) {
		
		Long id = Long.valueOf(EncryptionUtil.decode(cipher));
		User loggedIUser = userService.getLoggedInUser();
		User user = userService.getUser(id, true, loggedIUser);
		model.addAttribute("user", user);
		return "/user/show";
	}
	
	@RequestMapping(value="/user/update/{cipher}")
	public String updateUser(@PathVariable String cipher, Model model) {
		
		Long id = Long.valueOf(EncryptionUtil.decode(cipher));
		User loggedInUser = userService.getLoggedInUser();
		User user = userService.getUser(id, true, loggedInUser);
		model.addAttribute("user", user);
		model.addAttribute("id", user.getId());
		model.addAttribute("version", user.getVersion());
		return "/user/update";
	}
	
	@RequestMapping(value="/user/update", method=RequestMethod.POST)
	public String userUpdate(User user, BindingResult formBinding, Model model) {
		
		user.setUpdateOperation(true);
		userValidator.validate(user, formBinding);
		if(formBinding.hasErrors()) {
			model = populateModelForAdd(model, user);
			return "/user/update";
		}
		user.setId(Long.parseLong(model.asMap().get("id") + ""));
		user.setVersion(Long.valueOf(model.asMap().get("version") + ""));
		User loggedInUser = userService.getLoggedInUser();
		userService.updateUser(user.getId(), user, loggedInUser);
		model.asMap().remove("id");
		model.asMap().remove("version");
		String message = "User updated successfully";
		return "redirect:/user/list?message="+message;
	}
	
	@RequestMapping(value="/user/delete/{cipher}")
	public String deleteUser(@PathVariable String cipher) {
		
		Long id = Long.parseLong(EncryptionUtil.decode(cipher));
		String message = "";
		try {
			userService.deleteUser(id);
			message = "User deleted successfully";
		} catch (Exception e) {
			e.printStackTrace();
			message = "Unable to delete user";
		}
		return "redirect:/user/list?message="+message;
	}
	
	@RequestMapping(value="/restapi/getProfilePicture", method=RequestMethod.GET)
	@ResponseBody
	public void apiFetchVenuePicture(String email,
									HttpServletResponse httpServletResponse){
		
		User user = userService.getUserByEmail(email, false);
		File file = new File(user.getProfilePicUrl());
		httpServletResponse.setHeader("Expires", "0");
		httpServletResponse.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		httpServletResponse.setHeader("Pragma", "public");
		httpServletResponse.setHeader("Content-Type", "image/*");
		httpServletResponse.setHeader("Content-Length", String.valueOf(file.length()));
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			FileCopyUtils.copy(fileInputStream, httpServletResponse.getOutputStream());
			fileInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
