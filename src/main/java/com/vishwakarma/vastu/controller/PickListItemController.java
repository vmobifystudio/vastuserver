package com.vishwakarma.vastu.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.vishwakarma.vastu.model.PickListItem;
import com.vishwakarma.vastu.model.PickListItem.PickListType;
import com.vishwakarma.vastu.service.PickListItemService;

@Controller
@SessionAttributes({"id","retUrl","returnPage"})
public class PickListItemController {

	@Resource
	private PickListItemService pickListItemService;
	
	private final static Logger log = LoggerFactory.getLogger(PickListItemController.class);
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields("id");
	}
	
	private List<String> getListTypes() {
		
		List<String> listTypes = new ArrayList<String>();
		for (PickListType pickListType : PickListType.values()) {
			listTypes.add(pickListType.name());
		}
		return listTypes;
	}
	
	@RequestMapping("/pickListItem/list")
	public String pickListItemList(final ModelMap model,
			@RequestParam(defaultValue="1") Integer pageNumber,
			@RequestParam(required = false) String message,
			@RequestParam(required = false) String searchTerm) {
		model.addAttribute("message", message);
		Page<PickListItem> page;
		if(StringUtils.isBlank(searchTerm)) {
			page = pickListItemService.getPickListItems(pageNumber);
		} else {
			page = pickListItemService.search(pageNumber, searchTerm);
		}
		
		int current = page.getNumber() + 1;
	    int begin = Math.max(1, current - 5);
	    int end = Math.min(begin + 10, page.getTotalPages());

	    model.addAttribute("page", page);
	    model.addAttribute("beginIndex", begin);
	    model.addAttribute("endIndex", end);
	    model.addAttribute("currentIndex", current);
	    model.addAttribute("pickListItems", page.getContent());

		log.info("PickListItems--" + page);
		return "/pickListItem/list";
	}
	
	@RequestMapping(value="/pickListItem/add", method = RequestMethod.GET)
	public String pickListItemAdd(Model model, String retUrl) {
		
		model.addAttribute("pickListItem", new PickListItem());
		model.addAttribute("listTypes", getListTypes());
		if(StringUtils.isNotBlank(retUrl)) {
			model.addAttribute("retUrl", retUrl);
		}
		return "/pickListItem/add";
	}
	
	@RequestMapping(value="/pickListItem/add", method = RequestMethod.POST)
	public String addPickListItem(@Valid PickListItem pickListItem,
								BindingResult formBinding,
								Map<String, Object> model,
								HttpSession httpSession) {
		
		if(formBinding.hasErrors()) {
			model.put("pickListItem", pickListItem);
			model.put("listTypes", getListTypes());
			return "/pickListItem/add";
		}
		pickListItemService.addPickListItem(pickListItem);
		
		if (model.get("retUrl") != null) {
			String retUrl = model.get("retUrl").toString();
			model.remove("retUrl");
			return "redirect:"+retUrl;
		}
		String message = "Added Successfully!";
		return "redirect:/pickListItem/list?message=" + message;
	}
	
	@RequestMapping(value="/pickListItem/{pickListItemId}", method=RequestMethod.GET)
	public ModelAndView pickListItemUpdate(@PathVariable String pickListItemId, 
									Model model, 
									@RequestParam(defaultValue="1") Integer returnPage) {

		PickListItem pickListItem = pickListItemService.getPickListItem(Long.parseLong(pickListItemId));
		model.addAttribute("id", pickListItem.getId());
		log.info("PickListItem Id --- " + pickListItemId + " --- " + Long.parseLong(pickListItemId));
		ModelAndView mav = new ModelAndView("pickListItem/update");
		mav.getModelMap().put("pickListItem", pickListItem);
		mav.getModelMap().put("listTypes", getListTypes());
		mav.getModelMap().put("returnPage", returnPage);
		return mav;
	}
	
	@RequestMapping(value="/pickListItem/update", method=RequestMethod.POST)
	public String updatePickListItem(@Valid PickListItem pickListItem,
									BindingResult formBinding,
									Map<String, Object> model,
									HttpSession httpSession) {
		if(formBinding.hasErrors()) {
			model.put("pickListItem", pickListItem);
			model.put("listTypes", getListTypes());
			return "/pickListItem/update";
		}
		pickListItem.setId(Long.parseLong(model.get("id").toString()));
		pickListItemService.updatePickListItem(pickListItem.getId(), pickListItem);
		
		model.remove("id");
		
		String returnPage = model.get("returnPage").toString();
		model.remove("returnPage");
		
		String message = "Updated Successfully!";
		return "redirect:/pickListItem/list?pageNumber="+returnPage+"&message=" + message;
	}
	
	@RequestMapping(value="/show/pickListItem/{pickListItemId}", method=RequestMethod.GET)
	public ModelAndView pickListItemDetails(@PathVariable String pickListItemId,
											Model model) {	
		PickListItem pickListItem = pickListItemService.getPickListItem(Long.parseLong(pickListItemId));
		model.addAttribute("id", pickListItem.getId());
		log.info("PickListItem Id --- " + pickListItemId + " --- " + Long.parseLong(pickListItemId));
		ModelAndView mav = new ModelAndView("/pickListItem/show");
		mav.getModelMap().put("pickListItem", pickListItem);
		mav.getModelMap().put("listTypes", getListTypes());
		mav.getModelMap().put("readOnly", true);
		return mav;
	}
	
	/*@RequestMapping(value="/pickListItem/delete/{pickListItemId}")
	public String deletePickListItem(@PathVariable String pickListItemId) {
		log.info("PickListItem Id --- " + pickListItemId + " --- " + Long.parseLong(pickListItemId));
		boolean result = pickListItemService.deletePickListItem(Long.parseLong(pickListItemId));
		String message = "Unable to delete selected item";
		if(result) {
			message = "Deleted Successfully!";
		}
		return "redirect:/pickListItem/list?message=" + message;
	}
	
	@RequestMapping(value="/pickListItem/multiDelete")
	public String multiDeletePickListItem(@RequestParam Long[] selectedIds) {
		
		String message = null;
		if(null == selectedIds) {
			message = "No pickListItem is selected.Please select at-least one pickListItem to delete";
		} else {
			for (Long pickListItemId : selectedIds) {
				try {
					pickListItemService.deletePickListItem(pickListItemId);
				} catch (Exception e) {
					log.warn("Error while deleting PickListItem with id - " + pickListItemId);
					log.warn(e.getMessage());
				}
			}
			message = "Successfully deleted selected PickListItems";
		}
		return "redirect:/pickListItem/list?message=" + message;
	}*/
}

