package com.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dto.CategoryBrandProductDto;
import com.dto.CheckoutDto;
import com.dto.CurrentUserDto;
import com.dto.DashboardDto;
import com.dto.FieldErrorDTO;
import com.dto.ProductDto;
import com.exception.DataNotExistException;
import com.exception.InsertUserException;
import com.service.ICateogyService;
import com.service.IProductService;

@RestController
@RequestMapping(value = "/rs/mobile")
public class RestProductController {

	private static final int MAXRESULT = 200;
	private static final Logger LOGGER = LogManager.getLogger(RestProductController.class);

	@Autowired
	private ICateogyService cateogyService;
	@Autowired
	private IProductService productService;
	@Autowired
	private MessageSource messageSource;
	

	@RequestMapping(method = RequestMethod.GET, value = "/dashboard", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Map<String, Object> dashbaord(HttpServletRequest request, @RequestParam(required = false) String rtype) {
		LOGGER.info("dashbaord request called with rtype:-"+rtype);
		Map<String, Object> responseMap = new HashMap<>();
		DashboardDto dashboardDto = cateogyService.getDashboardProductWithCategoryBrandList();
		LOGGER.info("dashboard  response with dashboardDto" + dashboardDto);
		responseMap.put("success", Boolean.TRUE);
		responseMap.put("data", dashboardDto);
		return responseMap;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/productBySubCategory", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Map<String, Object> productListBySubCatgory(HttpServletRequest request, @RequestParam(required = true) Integer subCategoryId) {
		LOGGER.info("productListBySubCatgory called with subCategoryId" + subCategoryId);
		Map<String, Object> responseMap = new HashMap<>();
		if (subCategoryId != null && subCategoryId > 0) {
			List<ProductDto> productDtoList = productService.getProductListBySubCategory(subCategoryId);
			LOGGER.info("productListBySubCatgory  response with productDto" + productDtoList);
			responseMap.put("success", Boolean.TRUE);
			responseMap.put("data", productDtoList);
			return responseMap;
		}
		responseMap.put("message", messageSource.getMessage("product.subcategory.invalid", null, Locale.ENGLISH));
		responseMap.put("success", Boolean.FALSE);
		return responseMap;

	}

	
	@RequestMapping(method = RequestMethod.GET, value = "/allproducts", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Map<String, Object> allProducts(@RequestParam(required = false) Integer pageNum) {
	    if (pageNum == null || pageNum <= 0) {
	      pageNum = 1;
	    }
		Map<String, Object> responseMap = new HashMap<>();
		List<ProductDto> productDtoList = productService.allProducts(pageNum, MAXRESULT);
		responseMap.put("success", Boolean.TRUE);
		responseMap.put("productList", productDtoList);
		return responseMap;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/productByCategory", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Map<String, Object> productListByCatgory(HttpServletRequest request, @RequestParam(required = true) Integer categoryId) {
		LOGGER.info("productListByCatgory called with CategoryId" + categoryId);
		Map<String, Object> responseMap = new HashMap<>();
		if (categoryId != null && categoryId > 0) {
			List<ProductDto> productDtoList = productService.getProductListByCategory(categoryId);
			LOGGER.info("productListByCatgory  response with productDto" + productDtoList);
			responseMap.put("success", Boolean.TRUE);
			responseMap.put("data", productDtoList);
			return responseMap;
		}
		responseMap.put("message", messageSource.getMessage("product.category.invalid", null, Locale.ENGLISH));
		responseMap.put("success", Boolean.FALSE);
		return responseMap;
	}
	  @RequestMapping(method = RequestMethod.GET, value = "/allproductData", produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody Map<String, Object> allProductView(@RequestParam(required = false) Integer pageNum) {
		    if (pageNum == null || pageNum <= 0) {
		      pageNum = 1;
		    }
			Map<String, Object> responseMap = new HashMap<>();
			List<ProductDto> productDtoList = productService.allProducts(pageNum, MAXRESULT);
			responseMap.put("success", Boolean.TRUE);
			responseMap.put("productList", productDtoList);
			return responseMap;
		}
	@RequestMapping(value = "/detail/{productId}", method = RequestMethod.GET)
	public Map<String, Object> getAuditDetail(@PathVariable int productId) {
		LOGGER.info("getAuditDetail called with productId" + productId);
		Map<String, Object> data = new HashMap<String, Object>();
		ProductDto productDto = productService.findWithDetailsById(productId);
		LOGGER.info("getAuditDetail  response with productDto" + productDto);
		if(productDto != null){
			data.put("success", Boolean.TRUE);
			data.put("data", productDto);
		}else{
			data.put("success", Boolean.FALSE);
			data.put("message", messageSource.getMessage("product.nofound", null, Locale.ENGLISH));
		}
		return data;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/productWithType", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Map<String, Object> getproductBrandListBySubCategory(HttpServletRequest request, @RequestParam(required = true) Integer subCategoryId) {
		LOGGER.info("getproductBrandListBySubCategory called with subCategoryId" + subCategoryId);
		Map<String, Object> responseMap = new HashMap<>();
		if (subCategoryId != null && subCategoryId > 0) {
			List<CategoryBrandProductDto> categoryBrandProductDtoList = productService.getproductWithTypeListBySubCategory(subCategoryId);
			LOGGER.info("getproductBrandListBySubCategory  response with productDto" + categoryBrandProductDtoList);
			responseMap.put("success", Boolean.TRUE);
			responseMap.put("data", categoryBrandProductDtoList);
			return responseMap;
		}
		responseMap.put("message", messageSource.getMessage("product.subcategory.invalid", null, Locale.ENGLISH));
		responseMap.put("success", Boolean.FALSE);
		return responseMap;
	}


	  /*@RequestMapping(value = "/checkoutCart", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	  @ResponseBody
	  public Map<String, Object> checkoutCart(@RequestBody @Valid CartCheckoutDto checkoutCartDto) {
		LOGGER.info("checkoutCart request called with checkoutCartDto:-"+checkoutCartDto);
	    Map<String, Object> responseMap = new HashMap<String, Object>();
	    boolean success =true; 
//	    productService.checkoutCart(checkoutCart);
	    responseMap.put("success", success);
	    if (success) {
	      responseMap.put("message", messageSource.getMessage("checkout.success", null, Locale.ENGLISH));
	    } else {
	      responseMap.put("message", messageSource.getMessage("checkout.failed", null, Locale.ENGLISH));
	    }

	    return responseMap;
	  }
	  */
	  @RequestMapping(value = "/checkout", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	  @ResponseBody
	  public Map<String, Object> checkout(@RequestBody @Valid CheckoutDto cart, Errors errors) {
		  LOGGER.info("checkout request called with checkout:-"+cart);
		  Map<String, Object> responseMap = new HashMap<String, Object>();
		  boolean hasError = false;
		  List<FieldErrorDTO> fieldErrorDTOs = new ArrayList<>();
		  if(errors.hasErrors()){
			  hasError = true;
			  List<FieldError> fieldErrors = errors.getFieldErrors();
			  for (FieldError fieldError: fieldErrors) {
				  String localizedErrorMessage = resolveLocalizedErrorMessage(fieldError);
				  FieldErrorDTO error = new FieldErrorDTO(fieldError.getField(), localizedErrorMessage);
				  fieldErrorDTOs.add(error);
			  }
			  responseMap.put("fieldErrors", fieldErrorDTOs);
			  responseMap.put("success", Boolean.FALSE);
			  LOGGER.info("checkout request fieldErrors return  with fieldErrors:-"+fieldErrors);
			  return responseMap;
		  }
		  if(!hasError){
			  try {
				  CurrentUserDto currentUserDto = (CurrentUserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				  boolean success = productService.checkoutCart(currentUserDto, cart, fieldErrorDTOs);
				  if (success) {
					  responseMap.put("success", Boolean.TRUE);
					  responseMap.put("message", messageSource.getMessage("checkout.success", null, Locale.ENGLISH));
				  } else if(!fieldErrorDTOs.isEmpty() && fieldErrorDTOs.size() > 0){
					  responseMap.put("fieldErrors", fieldErrorDTOs);
					  responseMap.put("success", Boolean.FALSE);
				  } else {
					  responseMap.put("success", Boolean.FALSE);
					  responseMap.put("message", messageSource.getMessage("checkout.failed", null, Locale.ENGLISH));
				  }
			  } catch (InsertUserException e) {
				  responseMap.put("success", Boolean.FALSE);
				  LOGGER.info("error while adding user data with cart ="+cart);
				  responseMap.put("message", messageSource.getMessage("checkout.failed.addUser", null, Locale.ENGLISH));
			  } catch (DataNotExistException e) {
				  LOGGER.info("product is no more avaiable for id ="+e.getMessage());
				  responseMap.put("success", Boolean.FALSE);
				  responseMap.put("message", e.getMessage());
			  }
		  }
		  LOGGER.info("checkout request  return  with responseMap:-"+responseMap);
		  return responseMap;
	  }
	  
	  @RequestMapping(value = "/searchProduct", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	  public @ResponseBody Map<String, Object> searchProduct(@RequestParam String query) {
		  Map<String, Object> responseMap = new HashMap<String, Object>();
		  responseMap.put("data", productService.searchProduct(query.trim()));
		  responseMap.put("success", Boolean.TRUE);
		  return responseMap;
	  }
	  
	  private String resolveLocalizedErrorMessage(FieldError fieldError) {
		    Locale currentLocale = LocaleContextHolder.getLocale();
		    String localizedErrorMessage = messageSource.getMessage(fieldError, currentLocale);

		    // If the message was not found, return the most accurate field error code instead.
		    // You can remove this check if you prefer to get the default error message.
		    if (localizedErrorMessage.equals(fieldError.getDefaultMessage())) {
		      String[] fieldErrorCodes = fieldError.getCodes();
		      localizedErrorMessage = fieldErrorCodes[0];
		    }

		    return localizedErrorMessage;
		  }
}