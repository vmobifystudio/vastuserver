/*package com.vishwakarma.vastu.controller;

import java.util.List;
import java.util.Set;

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
import org.springframework.web.bind.annotation.SessionAttributes;

import com.vishwakarma.vastu.model.LineItem;
import com.vishwakarma.vastu.model.Product;
import com.vishwakarma.vastu.service.LineItemService;
import com.vishwakarma.vastu.service.ProductService;
import com.vishwakarma.vastu.service.UserService;
import com.vishwakarma.vastu.utils.CollectionEditor;
import com.vishwakarma.vastu.utils.EncryptionUtil;

@Controller
@SessionAttributes(value={"id","version","retUrl","retPage"})
public class LineItemController {
	
	@Resource
	private UserService userService;
	
	@Resource
	private LineItemService lineItemService;
	
	@Resource
	private ProductService productService;
	
	@InitBinder(value="LineItem")
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields("id", "version");
		binder.registerCustomEditor(Set.class, new CollectionEditor(Set.class,productRepository));
	}
	
	private Model populateModelForAdd(Model model, LineItem LineItem) {
		model.addAttribute("LineItem", LineItem);
		model.addAttribute("products", productService.getproduct());
		return model;
	}
	
	@RequestMapping(value="/lineItem/add")
	public String addLineItem(Model model) {
		model = populateModelForAdd(model, new LineItem());
		return "/LineItem/add";
	}
	
	@RequestMapping(value="/LineItem/add", method=RequestMethod.POST)
	public String LineItemAdd(LineItem LineItem, BindingResult formBinding, Model model) {
		
		if(formBinding.hasErrors()) {
			model = populateModelForAdd(model, LineItem);
			return "/LineItem/add";
		}
		LineItemService.createLineItem(LineItem, userService.getLoggedInUser(), false);
		String message = "LineItem added successfully";
		return "redirect:/LineItem/list?message="+message;
	}

	@RequestMapping(value="/LineItem/list")
	public String getLineItemList(Model model,
										@RequestParam(defaultValue="1") Integer pageNumber,
										@RequestParam(required=false) String message,
										@RequestParam(required=false) String searchTerm) {
		model.addAttribute("message", message);
		Page<LineItem> page;
		if(StringUtils.isNotBlank(searchTerm)) {
			page = LineItemService.searchLineItems(pageNumber, searchTerm, userService.getLoggedInUser(), false);
		} else {
			page = LineItemService.getLineItems(pageNumber, userService.getLoggedInUser(), true);
		}
		int currentIndex = page.getNumber() + 1;
		int beginIndex = Math.max(1, currentIndex - 5);
		int endIndex = Math.min(beginIndex + 10, page.getTotalPages());
		
		model.addAttribute("page", page);
		model.addAttribute("currentIndex", currentIndex);
		model.addAttribute("beginIndex", beginIndex);
		model.addAttribute("endIndex", endIndex);
		model.addAttribute("searchTerm", searchTerm);
		
		model.addAttribute("LineItems", page.getContent());
		return "/LineItem/list";
	}
	
	@RequestMapping(value="/LineItem/show/{cipher}")
	public String showLineItem(@PathVariable String cipher, Model model) {
		
		Long id = Long.valueOf(EncryptionUtil.decode(cipher));
		LineItem LineItem = LineItemService.getLineItem(id, true, userService.getLoggedInUser(), true, false);
		model.addAttribute("LineItem", LineItem);
		return "/LineItem/show";
	}
	
	@RequestMapping(value="/lineItem/update/{cipher}")
	public String updateLineItem(@PathVariable String cipher, Model model) {
		
		Long id = Long.valueOf(EncryptionUtil.decode(cipher));
		LineItem LineItem = lineItemService.getLineItem(id, true, userService.getLoggedInUser(), true, false);
		List<Product> products = productService.getproduct();
		model.addAttribute("LineItem", LineItem);
		model.addAttribute("products", products);
		model.addAttribute("id", LineItem.getId());
		model.addAttribute("version", LineItem.getVersion());
		return "/lineItem/update";
	}
	
	@RequestMapping(value="/lineItem/update", method=RequestMethod.POST)
	public String LineItemUpdate(LineItem LineItem, BindingResult formBinding, Model model) {
		
		LineItem.setUpdateOperation(true);
		if(formBinding.hasErrors()) {
			model = populateModelForAdd(model, LineItem);
			return "/lineItem/update";
		}
		LineItem.setId(Long.parseLong(model.asMap().get("id") + ""));
		LineItem.setVersion(Long.valueOf(model.asMap().get("version") + ""));
		lineItemService.updateLineItem(LineItem.getId(), LineItem, userService.getLoggedInUser(), true, false);
		model.asMap().remove("id");
		model.asMap().remove("version");
		String message = "Order updated successfully";
		return "redirect:/lineItem/list?message="+message;
	}
	
	@RequestMapping(value="/lineItem/delete/{cipher}")
	public String deleteLineItem(@PathVariable String cipher) {
		
		Long id = Long.parseLong(EncryptionUtil.decode(cipher));
		String message = "";
		try {
			lineItemService.deleteLineItem(id);
			message = "LineItem deleted successfully";
		} catch (Exception e) {
			e.printStackTrace();
			message = "Unable to delete LineItem";
		}
		return "redirect:/lineItem/list?message="+message;
	}

	@RequestMapping(value ="/restapi/LineItem/create",  method=RequestMethod.POST)
	@ResponseBody
	@PreAuthorize(value="permitAll")
	public ApiResponse placeOrder(@RequestBody (required= true) LineItemDTO LineItemDTO) {
		
		ApiResponse apiResponse = new ApiResponse(true);
		try {
			LineItem LineItem = new LineItem();
			
			LineItem.setCode(LineItemDTO.getCode());
			LineItem.setName(LineItemDTO.getName());
			LineItem.setAddress(LineItemDTO.getAddress());
			LineItem.setMobileNumber(LineItemDTO.getMobileNumber());
			LineItem.setPin(LineItemDTO.getPin());
			if(null != LineItemDTO.getLineItems()) {
				for(String lineitemCode : LineItemDTO.getLineItems()) {
					LineItem lineItem = lineItemService.getLineItemByCode(lineitemCode, true, userService.getLoggedInUser(), true, true);
					LineItem.addLineItems(lineItem);
				}
			}
			BindingResult LineItemBinder = new DataBinder(LineItem).getBindingResult();
			//LineItemValidator.validate(LineItem, LineItemBinder);
			if(LineItemBinder.hasErrors()) {
				String errorMessage = "";
				for(ObjectError objectError : LineItemBinder.getAllErrors()) {
					if(errorMessage.isEmpty()) {
						errorMessage = objectError.getDefaultMessage();
					} else {
						errorMessage = errorMessage + "," +  objectError.getDefaultMessage();
					}
				}
				throw new VastuException(errorMessage, "400");
			}
			LineItem = LineItemService.createLineItem(LineItem, true);
			apiResponse.addData("LineItem", new LineItemDTO(LineItem));
			
		} catch(VastuException e) {
			e.printStackTrace();
			apiResponse.setError(e.getExceptionMsg(), e.getErrorCode());
		}
		
		return apiResponse;
	}

}
*/