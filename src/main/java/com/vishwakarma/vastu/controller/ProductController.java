package com.vishwakarma.vastu.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.vishwakarma.vastu.api.ApiResponse;
import com.vishwakarma.vastu.dto.ProductDTO;
import com.vishwakarma.vastu.exception.VastuException;
import com.vishwakarma.vastu.model.Product;
import com.vishwakarma.vastu.mvc.validator.ProductValidatior;
import com.vishwakarma.vastu.service.ProductService;
import com.vishwakarma.vastu.service.UserService;
import com.vishwakarma.vastu.utils.EncryptionUtil;

@Controller
@SessionAttributes(value={"id","version","retUrl","retPage"})
public class ProductController {

	@Resource
	private ProductService productService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private ProductValidatior productValidatior;
	
	@InitBinder(value="product")
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields("id", "version");
		binder.setValidator(productValidatior);
	}
	
	private Model populateModelForAdd(Model model, Product product) {
		model.addAttribute("product", product);
		return model;
	}
	
	@RequestMapping(value="/product/add")
	public String addProduct(Model model) {
		model = populateModelForAdd(model, new Product());
		return "/product/add";
	}
	
	@RequestMapping(value="/product/add", method=RequestMethod.POST)
	public String productAdd(Product product, 
							BindingResult formBinding, 
							Model model,
							@RequestParam(required=true, value="productPic") MultipartFile productPic) {
		
		productValidatior.validate(product, formBinding);
		if(null != productPic && !productPic.isEmpty()) {
			
			String contentType = productPic.getContentType();
			if(!contentType.contains("image/")) {
				formBinding.addError(new FieldError("product", "productPicUrl", "Please select valid product Pic"));
			} else if(productPic.getSize() > 5242880) { //Maximum size = 5MB
				formBinding.addError(new FieldError("product", "productPicUrl", "Please select product Picture less than 5MB"));
			}
			
			try {
				product.setProductPicData(productPic.getBytes());
				product.setFileName(productPic.getOriginalFilename());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			formBinding.addError(new FieldError("product", "productPicUrl", "Please select valid product Pic"));
		}

		if(formBinding.hasErrors()) {
			model = populateModelForAdd(model, product);
			return "/product/add";
		}
		
		productService.createproduct(product, userService.getLoggedInUser(), false);
		String message = "Product added successfully";
		return "redirect:/product/list?message="+message;
	}

	@RequestMapping(value="/product/list")
	public String getProductList(Model model,
										@RequestParam(defaultValue="1") Integer pageNumber,
										@RequestParam(required=false) String message,
										@RequestParam(required=false) String searchTerm) {
		model.addAttribute("message", message);
		Page<Product> page;
		if(StringUtils.isNotBlank(searchTerm)) {
			page = productService.searchproduct(pageNumber, searchTerm, userService.getLoggedInUser(), false);
		} else {
			page = productService.getproducts(pageNumber, userService.getLoggedInUser(), false);
		}
		int currentIndex = page.getNumber() + 1;
		int beginIndex = Math.max(1, currentIndex - 5);
		int endIndex = Math.min(beginIndex + 10, page.getTotalPages());
		
		model.addAttribute("page", page);
		model.addAttribute("currentIndex", currentIndex);
		model.addAttribute("beginIndex", beginIndex);
		model.addAttribute("endIndex", endIndex);
		model.addAttribute("searchTerm", searchTerm);
		
		model.addAttribute("products", page.getContent());
		
		return "/product/list";
	}
	
	@RequestMapping(value="/product/show/{cipher}")
	public String showProduct(@PathVariable String cipher, Model model) {
		
		Long id = Long.valueOf(EncryptionUtil.decode(cipher));
		Product product = productService.getproduct(id, true, userService.getLoggedInUser(), true, false);
		model.addAttribute("product", product);
		List<String> relatedTo = new ArrayList<String>();
		if(product.getIsBedroomScore()) {
			relatedTo.add("Bedroom");
		}
		if(product.getIsBathRoomScore()) {
			relatedTo.add("Bathroom");
		}
		if(product.getIsKitchenroomScore()) {
			relatedTo.add("Kitchenroom");
		}
		if(product.getIsHallScore()) {
			relatedTo.add("Hall");
		}
		if(product.getIsGalleryScore()) {
			relatedTo.add("Gallery");
		}
		if(product.getIsDirectioncutScore()) {
			relatedTo.add("DirectionCut");
		}
		if(product.getIsEnterenceScore()) {
			relatedTo.add("Enterence");
		}
		if(product.getIsWindowScore()) {
			relatedTo.add("Window");
		}
		model.addAttribute("relatedTo", relatedTo);
		return "/product/show";
	}
	
	@RequestMapping(value="/product/update/{cipher}")
	public String updateProduct(@PathVariable String cipher, Model model) {
		
		Long id = Long.valueOf(EncryptionUtil.decode(cipher));
		Product product = productService.getproduct(id, true, userService.getLoggedInUser(), true, false);
		model.addAttribute("product", product);
		model.addAttribute("id", product.getId());
		model.addAttribute("version", product.getVersion());
		model = populateModelForAdd(model, product);
		return "/product/update";
	}
	
	@RequestMapping(value="/product/update", method=RequestMethod.POST)
	public String productUpdate(Product product, BindingResult formBinding, Model model) {
		
		product.setUpdateOperation(true);
		productValidatior.validate(product, formBinding);
		if(formBinding.hasErrors()) {
			model = populateModelForAdd(model, product);
			return "/product/update";
		}
		product.setId(Long.parseLong(model.asMap().get("id") + ""));
		product.setVersion(Long.valueOf(model.asMap().get("version") + ""));
		productService.updateproduct(product.getId(), product, userService.getLoggedInUser(), true, false);
		model.asMap().remove("id");
		model.asMap().remove("version");
		String message = "Product updated successfully";
		return "redirect:/product/list?message="+message;
	}
	
	@RequestMapping(value="/product/delete/{cipher}")
	public String deleteProduct(@PathVariable String cipher) {
		
		Long id = Long.parseLong(EncryptionUtil.decode(cipher));
		String message = "";
		try {
			productService.deleteproduct(id);
			message = "Product deleted successfully";
		} catch (Exception e) {
			e.printStackTrace();
			message = "Unable to delete Product";
		}
		return "redirect:/product/list?message="+message;
	}
	
	/**
	 * API to get @ProductList
	 * @param productDTOJson is Json String
	 * @return 
	 */
	
	@RequestMapping(value="/restapi/product/list", method=RequestMethod.GET) 
	@ResponseBody
	public ApiResponse getProducts(@RequestParam(required=true, defaultValue = "1") Integer pageNumber,
			@RequestParam(required = false) String message, 
			@RequestParam(required=false) String searchTerm) {
		
		ApiResponse apiResponse = new ApiResponse(true);
		
		try{
			List<Product> products=productService.getproduct();
			List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
			for (Product product : products) {
				ProductDTO productDTO = new ProductDTO(product);
				productDTOs.add(productDTO);
			}
		
			apiResponse.addData("products", productDTOs);
			apiResponse.addData("maxPages", products);
			apiResponse.addData("currentPage", pageNumber);
			
		} catch(VastuException e) {
			e.printStackTrace();
			apiResponse.setError(e.getExceptionMsg(), e.getErrorCode());
		}
		return apiResponse;
	}
	
	/**
	 * API to get @ProductPicURL
	 * @param code is 
	 * @return 
	 */
	
	@RequestMapping(value="/restapi/product/getLogoPic", method=RequestMethod.GET)
	@ResponseBody
	public void apiFetchVenuePicture(String code,
									HttpServletResponse httpServletResponse){
		
		Product product = productService.getproductByCode(code, true, true, true);
		File file = new File(product.getProductPicUrl());
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
	
	/**
	 * API to get @ProductList as per Count
	 * @param count
	 * @return 
	 **/
	
	/*@Deprecated
	@RequestMapping(value="restapi/product/productsByVastuScore", method=RequestMethod.GET)
	@ResponseBody
	public ApiResponse getProductsByScore(@RequestParam(required=true) String kitchenScore, @RequestParam(required=true) String bedRoomScore,
										@RequestParam(required=true) String dinningScore, @RequestParam(required=true) String poojaScore,
										@RequestParam(required=true) String livingScore, @RequestParam(required=true) String enterenceScore, 
										@RequestParam(required=true, defaultValue = "1") Integer pageNumber) {
		ApiResponse apiResponse = new ApiResponse(true);
		
		try {
			
			Page<Product> page = null;
			
			if(kitchenScore == null && bedRoomScore == null && dinningScore == null && poojaScore== null && livingScore== null && enterenceScore == null) {
				System.out.println("Get IN");
				apiResponse.addData("products", null);
				return apiResponse;
				
			} 
			
			if(kitchenScore!=null || bedRoomScore!=null || dinningScore!=null || poojaScore!=null || livingScore!=null ||  enterenceScore!=null ) {
				
				Integer kitchenValue, bedRoomValue, dinningValue, poojaValue, livingValue, enterenceValue;
				List<Integer> list = new ArrayList<Integer>();
				int min =0; 
				if(kitchenScore.equalsIgnoreCase("null")) {
					kitchenValue = null;
				} else {
					kitchenValue = Integer.parseInt(kitchenScore);
					list.add(kitchenValue);
				}
				
				if(bedRoomScore.equalsIgnoreCase("null")) {
					bedRoomValue = null;
				} else {
					bedRoomValue = Integer.parseInt(bedRoomScore);
					list.add(bedRoomValue);
				}
				
				if(dinningScore.equalsIgnoreCase("null")) {
					dinningValue = null;
				} else {
					dinningValue = Integer.parseInt(dinningScore);
					list.add(dinningValue);
				}
				
				if(poojaScore.equalsIgnoreCase("null")) {
					poojaValue = null; 
				} else {
					poojaValue = Integer.parseInt(poojaScore);
					list.add(poojaValue);
				}
				
				if(livingScore.equalsIgnoreCase("null")) {
					livingValue = null;
				} else {
					 livingValue = Integer.parseInt(livingScore);
					 list.add(livingValue);
				}
				 
				if(enterenceScore.equalsIgnoreCase("null"))	{
					enterenceValue = null;
				} else {
					enterenceValue = Integer.parseInt(enterenceScore);
					list.add(enterenceValue);
				}
				
				if(!list.isEmpty()) {
					min = Collections.min(list);
				
				}
				
				if(kitchenValue != null && kitchenValue == min) {
					boolean isKitchen = false;
					page = productService.getKitchenProductsByScore(isKitchen, pageNumber, false);
				}else if(bedRoomValue != null && bedRoomValue == min) {
					boolean isBedRoom = false;
					page = productService.getBedRoomProductsByScore(isBedRoom, pageNumber, false);
				}else if(dinningValue != null && dinningValue == min) {
					boolean isDinning = false;
					page = productService.getDinningProductsByScore(isDinning, pageNumber, false);
				}else if(poojaValue !=null && poojaValue == min) {
					boolean isPooja = false;
					page = productService.getPoojaRoomProductsByScore(isPooja, pageNumber, false);
				}else if(livingValue != null && livingValue == min) {
					boolean isLiving = false;
					page = productService.getLivingRoomProductsByScore(isLiving, pageNumber, false);
				}else if(enterenceValue != null && enterenceValue == min) {
					boolean isEnterence = false;
					page = productService.getEnterenceProductsByScore(isEnterence, pageNumber, false);
				}
				List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
				if(page != null) {
					for(Product product : page) {
						ProductDTO productDTO = new ProductDTO(product);
						productDTOs.add(productDTO);
					}
					apiResponse.addData("products", productDTOs);
					apiResponse.addData("maxPages", page.getTotalPages());
					apiResponse.addData("currentPage", pageNumber);
				}
				
				
			}
			
		} catch(VastuException e) {
			e.printStackTrace();
			apiResponse.setError(e.getExceptionMsg(), e.getErrorCode());
		}
		return apiResponse;
	}*/
	
	/*
	 * API to get Products by Score
	 * (if one or more tiles has min score then it will show product for that tile)
	 * 
	 */
	
	@RequestMapping(value="/restapi/product/productByVastuScore", method=RequestMethod.GET)
	@ResponseBody
	public ApiResponse getProductByScore(@RequestParam(required=true) String bathRoomScore, @RequestParam(required=true) String kitchenScore,
										@RequestParam(required=true) String bedRoomScore, @RequestParam(required=true) String hallScore,
										@RequestParam(required=true) String galleryScore, @RequestParam(required=true) String directioncutScore,
										@RequestParam(required=true) String enterenceScore, @RequestParam(required=true) String windowScore,
 										@RequestParam(required=true, defaultValue = "1") Integer pageNumber) {
		ApiResponse apiResponse = new ApiResponse(true);
		
		try {
			
			Set<Product> allProductsByScore = new HashSet<Product>();
			
			if((StringUtils.isBlank(bathRoomScore) || bathRoomScore == null ) && (StringUtils.isBlank(kitchenScore) || kitchenScore == null )  && (StringUtils.isBlank(bedRoomScore) || bedRoomScore == null) && (StringUtils.isBlank(hallScore)||hallScore== null ) && (StringUtils.isBlank(galleryScore)||galleryScore== null) && (StringUtils.isBlank(directioncutScore)||directioncutScore == null ) && (StringUtils.isBlank(enterenceScore) || enterenceScore == null ) && (StringUtils.isBlank(windowScore)||windowScore == null )) {
				throw new VastuException("Please select at least one tile");
			} 
			
			if(bathRoomScore!=null || kitchenScore!=null || bedRoomScore!=null || hallScore!=null || galleryScore!=null ||  directioncutScore!=null || enterenceScore!=null || windowScore!=null) {
				
				Integer bathRoomValue, kitchenValue, bedRoomValue, hallValue, galleryValue, directioncutValue, enterenceValue, windowValue;
				List<Integer> list = new ArrayList<Integer>();
				int min =0; 
				
				if(bathRoomScore.equalsIgnoreCase("null")) {
					bathRoomValue = null;
				} else {
					bathRoomValue = Integer.parseInt(bathRoomScore);
					list.add(bathRoomValue);
				}
				
				if(kitchenScore.equalsIgnoreCase("null")) {
					kitchenValue = null;
				} else {
					kitchenValue = Integer.parseInt(kitchenScore);
					list.add(kitchenValue);
				}
				
				if(bedRoomScore.equalsIgnoreCase("null")) {
					bedRoomValue = null;
				} else {
					bedRoomValue = Integer.parseInt(bedRoomScore);
					list.add(bedRoomValue);
				}
				
				if(hallScore.equalsIgnoreCase("null")) {
					hallValue = null;
				} else {
					hallValue = Integer.parseInt(hallScore);
					list.add(hallValue);
				}
				
				if(galleryScore.equalsIgnoreCase("null")) {
					galleryValue = null; 
				} else {
					galleryValue = Integer.parseInt(galleryScore);
					list.add(galleryValue);
				}
				
				if(directioncutScore.equalsIgnoreCase("null")) {
					directioncutValue = null;
				} else {
					directioncutValue = Integer.parseInt(directioncutScore);
					 list.add(directioncutValue);
				}
				 
				if(enterenceScore.equalsIgnoreCase("null"))	{
					enterenceValue = null;
				} else {
					enterenceValue = Integer.parseInt(enterenceScore);
					list.add(enterenceValue);
				}
				
				if(windowScore.equalsIgnoreCase("null"))	{
					windowValue = null;
				} else {
					windowValue = Integer.parseInt(windowScore);
					list.add(windowValue);
				}
				
				if(!list.isEmpty()) {
					min = Collections.min(list);
				
				}
				
				if(bathRoomValue != null && bathRoomValue == min) {
					List<Product> bathRoomproducts = new ArrayList<Product>();
					boolean isBathRoom = false;
					bathRoomproducts = productService.getBathRoomProductsByScore2(isBathRoom, pageNumber, true);
					for(Product bathRoomProduct : bathRoomproducts) {
						allProductsByScore.add(bathRoomProduct);
					}
				}
				
				if(kitchenValue != null && kitchenValue == min) {
					List<Product> kitchenproducts = new ArrayList<Product>();
					boolean isKitchen = false;
					kitchenproducts = productService.getKitchenProductsByScore2(isKitchen, pageNumber, false);
					for(Product kitchenProduct : kitchenproducts) {
						allProductsByScore.add(kitchenProduct);
					}
				}
				if(bedRoomValue != null && bedRoomValue == min) {
					List<Product> bedRoomproducts = new ArrayList<Product>();
					boolean isBedRoom = false;
					bedRoomproducts = productService.getBedRoomProductsByScore2(isBedRoom, pageNumber, false);
					for(Product bedRoomProduct : bedRoomproducts) {
						allProductsByScore.add(bedRoomProduct);
					}
				}
				if(hallValue != null && hallValue == min) {
					List<Product> hallproducts = new ArrayList<Product>();
					boolean isHall = false;
					hallproducts = productService.getHallProductsByScore2(isHall, pageNumber, true);
					for(Product hallProduct : hallproducts) {
						allProductsByScore.add(hallProduct);
					}
				}
				if(galleryValue !=null && galleryValue == min) {
					List<Product> galleryproducts = new ArrayList<Product>();
					boolean isGallery = false;
					galleryproducts = productService.getGalleryProductsByScore2(isGallery, pageNumber, true);
					for(Product galleryProduct : galleryproducts) {
						allProductsByScore.add(galleryProduct);
					}
				}
				if(directioncutValue != null && directioncutValue == min) {
					List<Product> directioncutproducts = new ArrayList<Product>();
					boolean isDirectioncut = false;
					directioncutproducts = productService.getDirectioncutProductsByScore2(isDirectioncut, pageNumber, true);
					for(Product directioncutProduct : directioncutproducts) {
						allProductsByScore.add(directioncutProduct);
					}
				}
				if(enterenceValue != null && enterenceValue == min) {
					List<Product> enterenceproducts = new ArrayList<Product>();
					boolean isEnterence = false;
					enterenceproducts = productService.getEnterenceProductsByScore2(isEnterence, pageNumber, false);
					for(Product enterenceProduct : enterenceproducts) {
						allProductsByScore.add(enterenceProduct);
					}
				}
				if(windowValue != null && windowValue == min) {
					List<Product> windowproducts = new ArrayList<Product>();
					boolean isWindow = false;
					windowproducts = productService.getWindowProductsByScore2(isWindow, pageNumber, true);
					for(Product windowProduct : windowproducts) {
						allProductsByScore.add(windowProduct);
					}
				}
				List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
				if(allProductsByScore != null) {
					for(Product product : allProductsByScore) {
						ProductDTO productDTO = new ProductDTO(product);
						productDTOs.add(productDTO);
					}
					apiResponse.addData("products", productDTOs);
				}
			}
			
		} catch(VastuException e) {
			apiResponse = new ApiResponse(false);
			apiResponse.setError(e.getExceptionMsg(), e.getErrorCode());
			e.printStackTrace();
		}
		return apiResponse;
	}
	
}
