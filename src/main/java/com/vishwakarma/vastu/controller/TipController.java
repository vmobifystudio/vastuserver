package com.vishwakarma.vastu.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.vishwakarma.vastu.api.ApiResponse;
import com.vishwakarma.vastu.dto.TipDTO;
import com.vishwakarma.vastu.exception.VastuException;
import com.vishwakarma.vastu.model.Tip;
import com.vishwakarma.vastu.model.User;
import com.vishwakarma.vastu.mvc.validator.TipValidator;
import com.vishwakarma.vastu.service.TipService;
import com.vishwakarma.vastu.service.UserService;
import com.vishwakarma.vastu.utils.EncryptionUtil;

@Controller
@SessionAttributes(value={"id","version","retUrl","retPage"})
public class TipController {

	@Resource
	private TipService tipService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private TipValidator tipValidator;
	
	
	@InitBinder(value="tip")
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields("id", "version");
		binder.setValidator(tipValidator);
	}
	
	private Model populateModelForAdd(Model model, Tip tip) {
		model.addAttribute("tip", tip);
		return model;
	}
	
	@RequestMapping(value="/tip/add")
	public String addTip(Model model) {
		model = populateModelForAdd(model, new Tip());
		return "/tip/add";
	}
	
	@RequestMapping(value="/tip/add", method=RequestMethod.POST)
	public String tipAdd(Tip tip, BindingResult formBinding, Model model) {
		
		tipValidator.validate(tip, formBinding);
		if(formBinding.hasErrors()) {
			model = populateModelForAdd(model, tip);
			return "/tip/add";
		}
		tipService.createTip(tip, userService.getLoggedInUser(), false);
		String message = "Tip added successfully";
		return "redirect:/tip/list?message="+message;
	}

	@RequestMapping(value="/tip/list")
	public String getTipList(Model model,
										@RequestParam(defaultValue="1") Integer pageNumber,
										@RequestParam(required=false) String message,
										@RequestParam(required=false) String searchTerm) {
		model.addAttribute("message", message);
		Page<Tip> page;
		if(StringUtils.isNotBlank(searchTerm)) {
			page = tipService.searchTips(pageNumber, searchTerm, userService.getLoggedInUser(), false);
		} else {
			page = tipService.getTips(pageNumber, userService.getLoggedInUser(), false);
		}
		int currentIndex = page.getNumber() + 1;
		int beginIndex = Math.max(1, currentIndex - 5);
		int endIndex = Math.min(beginIndex + 10, page.getTotalPages());
		
		model.addAttribute("page", page);
		model.addAttribute("currentIndex", currentIndex);
		model.addAttribute("beginIndex", beginIndex);
		model.addAttribute("endIndex", endIndex);
		model.addAttribute("searchTerm", searchTerm);
		
		model.addAttribute("tips", page.getContent());
		
		return "/tip/list";
	}
	
	@RequestMapping(value="/tip/show/{cipher}")
	public String showTip(@PathVariable String cipher, Model model) {
		
		Long id = Long.valueOf(EncryptionUtil.decode(cipher));
		Tip tip = tipService.getTip(id, true, userService.getLoggedInUser(), true, false);
		model.addAttribute("tip", tip);
		return "/tip/show";
	}
	
	@RequestMapping(value="/tip/update/{cipher}")
	public String updateTip(@PathVariable String cipher, Model model) {
		
		Long id = Long.valueOf(EncryptionUtil.decode(cipher));
		Tip tip = tipService.getTip(id, true, userService.getLoggedInUser(), true, false);
		model.addAttribute("tip", tip);
		model.addAttribute("id", tip.getId());
		model.addAttribute("version", tip.getVersion());
		return "/tip/update";
	}
	
	@RequestMapping(value="/tip/update", method=RequestMethod.POST)
	public String tipUpdate(Tip tip, BindingResult formBinding, Model model) {
		
		tipValidator.validate(tip, formBinding);
		tip.setUpdateOperation(true);
		if(formBinding.hasErrors()) {
			model = populateModelForAdd(model, tip);
			return "/tip/update";
		}
		tip.setId(Long.parseLong(model.asMap().get("id") + ""));
		tip.setVersion(Long.valueOf(model.asMap().get("version") + ""));
		tipService.updateTip(tip.getId(), tip, userService.getLoggedInUser(), true, false);
		model.asMap().remove("id");
		model.asMap().remove("version");
		String message = "Tip updated successfully";
		return "redirect:/tip/list?message="+message;
	}
	
	@RequestMapping(value="/tip/delete/{cipher}")
	public String deleteTip(@PathVariable String cipher) {
		
		Long id = Long.parseLong(EncryptionUtil.decode(cipher));
		String message = "";
		try {
			tipService.deletetip(id);
			message = "Tip deleted successfully";
		} catch (Exception e) {
			e.printStackTrace();
			message = "Unable to delete Tip";
		}
		return "redirect:/tip/list?message="+message;
	}
	
	/**
	 * API to get @TipList with pagination
	 *	@param pageNumber
	 * @return 
	 */
	
	@RequestMapping(value="/restapi/tip/list", method=RequestMethod.GET) 
	@ResponseBody
	public ApiResponse getTips(@RequestParam(required=true, defaultValue = "1") Integer pageNumber,
			@RequestParam(required = false) String message, 
			@RequestParam(required=false) String searchTerm) {
		
		ApiResponse apiResponse = new ApiResponse(true);
		
		try{
			Page<Tip> page = null;
			//User loggedInUser = userService.getLoggedInUser();
			page = tipService.getTips(pageNumber,true);
			List<TipDTO> tipDTOs = new ArrayList<TipDTO>();
			for(Tip tip : page) {
				TipDTO tipDTO = new TipDTO(tip);
				tipDTOs.add(tipDTO);
			}
			apiResponse.addData("tips", tipDTOs);
			apiResponse.addData("maxPages", page.getTotalPages());
			apiResponse.addData("currentPage", pageNumber);
			
		} catch(VastuException e) {
			e.printStackTrace();
			apiResponse.setError(e.getExceptionMsg(), e.getErrorCode());
		}
		return apiResponse;
	}
}	
