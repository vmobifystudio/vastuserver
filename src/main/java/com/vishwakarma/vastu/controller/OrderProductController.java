package com.vishwakarma.vastu.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.vishwakarma.vastu.api.ApiResponse;
import com.vishwakarma.vastu.dto.OrderProductDTO;
import com.vishwakarma.vastu.exception.VastuException;
import com.vishwakarma.vastu.model.LineItem;
import com.vishwakarma.vastu.model.OrderProduct;
import com.vishwakarma.vastu.model.Product;
import com.vishwakarma.vastu.repository.LineItemRepository;
import com.vishwakarma.vastu.repository.ProductRepository;
import com.vishwakarma.vastu.service.LineItemService;
import com.vishwakarma.vastu.service.OrderProductService;
import com.vishwakarma.vastu.service.ProductService;
import com.vishwakarma.vastu.service.UserService;
import com.vishwakarma.vastu.utils.CollectionEditor;
import com.vishwakarma.vastu.utils.EncryptionUtil;

@Controller
@SessionAttributes(value={"id","version","retUrl","retPage"})
public class OrderProductController {

	@Resource
	private OrderProductService orderProductService;
	
	@Resource
	private ProductService productService;
	
	@Resource
	private ProductRepository productRepository;
	
	@Resource
	private UserService userService;
	
	@Resource
	private LineItemRepository lineItemRepository;
	
	@Resource
	private LineItemService lineItemService;
	
	@InitBinder(value="orderProduct")
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields("id", "version");
		binder.registerCustomEditor(Set.class, new CollectionEditor(Set.class,lineItemRepository));
	}
	
	private Model populateModelForAdd(Model model, OrderProduct orderProduct) {
		model.addAttribute("orderProduct", orderProduct);
		model.addAttribute("products", productService.getproduct());
		model.addAttribute("lineItems", orderProduct.getLineItems());
		return model;
	}
	
	@RequestMapping(value="/orderProduct/add")
	public String addorderProduct(Model model) {
		model = populateModelForAdd(model, new OrderProduct());
		return "/orderProduct/add";
	}
	
	@RequestMapping(value="/orderProduct/add", method=RequestMethod.POST)
	public String orderProductAdd(OrderProduct orderProduct, BindingResult formBinding, Model model) {
		
		if(formBinding.hasErrors()) {
			model = populateModelForAdd(model, orderProduct);
			return "/orderProduct/add";
		}
		orderProductService.createorderProduct(orderProduct, userService.getLoggedInUser(), false);
		String message = "orderProduct added successfully";
		return "redirect:/orderProduct/list?message="+message;
	}

	@RequestMapping(value="/orderProduct/list")
	public String getorderProductList(Model model,
										@RequestParam(defaultValue="1") Integer pageNumber,
										@RequestParam(required=false) String message,
										@RequestParam(required=false) String searchTerm) {
		model.addAttribute("message", message);
		Page<OrderProduct> page;
		if(StringUtils.isNotBlank(searchTerm)) {
			page = orderProductService.searchorderProducts(pageNumber, searchTerm, userService.getLoggedInUser(), false);
		} else {
			page = orderProductService.getorderProducts(pageNumber, userService.getLoggedInUser(), true);
		}
		int currentIndex = page.getNumber() + 1;
		int beginIndex = Math.max(1, currentIndex - 5);
		int endIndex = Math.min(beginIndex + 10, page.getTotalPages());
		
		model.addAttribute("page", page);
		model.addAttribute("currentIndex", currentIndex);
		model.addAttribute("beginIndex", beginIndex);
		model.addAttribute("endIndex", endIndex);
		model.addAttribute("searchTerm", searchTerm);
		
		model.addAttribute("orderProducts", page.getContent());
		return "/orderProduct/list";
	}
	
	@RequestMapping(value="/orderProduct/show/{cipher}")
	public String showorderProduct(@PathVariable String cipher, Model model) {
		
		Long id = Long.valueOf(EncryptionUtil.decode(cipher));
		OrderProduct orderProduct = orderProductService.getorderProduct(id, true, userService.getLoggedInUser(), true, true);
		model.addAttribute("orderProduct", orderProduct);
		model.addAttribute("lineItems", orderProduct.getLineItems());
		return "/orderProduct/show";
	}
	
	@RequestMapping(value="/orderProduct/update/{cipher}")
	public String updateorderProduct(@PathVariable String cipher, Model model) {
		
		Long id = Long.valueOf(EncryptionUtil.decode(cipher));
		OrderProduct orderProduct = orderProductService.getorderProduct(id, true, userService.getLoggedInUser(), true, false);
		List<Product> products = productService.getproduct();
		model.addAttribute("orderProduct", orderProduct);
		model.addAttribute("products", products);
		model.addAttribute("id", orderProduct.getId());
		model.addAttribute("version", orderProduct.getVersion());
		return "/orderProduct/update";
	}
	
	@RequestMapping(value="/orderProduct/update", method=RequestMethod.POST)
	public String orderProductUpdate(OrderProduct orderProduct, BindingResult formBinding, Model model) {
		
		orderProduct.setUpdateOperation(true);
		if(formBinding.hasErrors()) {
			model = populateModelForAdd(model, orderProduct);
			return "/orderProduct/update";
		}
		orderProduct.setId(Long.parseLong(model.asMap().get("id") + ""));
		orderProduct.setVersion(Long.valueOf(model.asMap().get("version") + ""));
		orderProductService.updateorderProduct(orderProduct.getId(), orderProduct, userService.getLoggedInUser(), true, false);
		model.asMap().remove("id");
		model.asMap().remove("version");
		String message = "Order updated successfully";
		return "redirect:/orderProduct/list?message="+message;
	}
	
	@RequestMapping(value="/orderProduct/delete/{cipher}")
	public String deleteorderProduct(@PathVariable String cipher) {
		
		Long id = Long.parseLong(EncryptionUtil.decode(cipher));
		String message = "";
		try {
			orderProductService.deleteorderProduct(id);
			message = "orderProduct deleted successfully";
		} catch (Exception e) {
			e.printStackTrace();
			message = "Unable to delete orderProduct";
		}
		return "redirect:/orderProduct/list?message="+message;
	}

	@RequestMapping(value ="/restapi/orderProduct/create",  method=RequestMethod.POST)
	@ResponseBody
	@PreAuthorize(value="permitAll")
	public ApiResponse placeOrder(@RequestBody (required= true) OrderProductDTO orderProductDTO) throws InvocationTargetException {
		
		ApiResponse apiResponse = new ApiResponse(true);
		try {
			OrderProduct orderProduct = new OrderProduct();
			orderProduct.setName(orderProductDTO.getName());
			orderProduct.setAddress(orderProductDTO.getAddress());
			orderProduct.setMobileNumber(orderProductDTO.getMobileNumber());
			orderProduct.setPin(orderProductDTO.getPin());
			
			BindingResult orderProductBinder = new DataBinder(orderProduct).getBindingResult();
			if(orderProductBinder.hasErrors()) {
				String errorMessage = "";
				for(ObjectError objectError : orderProductBinder.getAllErrors()) {
					if(errorMessage.isEmpty()) {
						errorMessage = objectError.getDefaultMessage();
					} else {
						errorMessage = errorMessage + "," +  objectError.getDefaultMessage();
					}
				}
				throw new VastuException(errorMessage, "400");
			}
			orderProduct = orderProductService.createorderProduct(orderProduct, true);
			
			//LineItem  Creation
			
			if(null != orderProductDTO.getProduct() && !orderProductDTO.getProduct().isEmpty()) {

				List<String> productCodes = new ArrayList<String>();
				for(String productCode : orderProductDTO.getProduct()) {
					
					if(!productCodes.contains(productCode)) {
						
						Product product = productService.getproductByCode(productCode, true, false, false);
						int frequency = Collections.frequency(orderProductDTO.getProduct(), productCode);
						
						LineItem lineItem = new LineItem();
						lineItem.setProduct(product);
						lineItem.setCount(frequency);
						lineItem.setOrderProduct(orderProduct);
						lineItem = lineItemService.createLineItem(lineItem, null, false);
						orderProduct.addLineItems(lineItem);
						productCodes.add(productCode);
					}
				}
				
				orderProduct = orderProductService.updateorderProduct(orderProduct.getId(), orderProduct, null, false, true);
			}
			
			//End Lineitem Creation
			apiResponse.addData("orderProduct", new OrderProductDTO(orderProduct));
			
		} catch(VastuException e) {
			e.printStackTrace();
			apiResponse.setError(e.getExceptionMsg(), e.getErrorCode());
		}
		
		return apiResponse;
	}
	
}
