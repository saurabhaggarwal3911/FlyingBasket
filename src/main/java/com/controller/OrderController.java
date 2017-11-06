package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dto.CheckoutDto;
import com.dto.ProductDto;
import com.service.IProductService;
import com.service.IUserTransactionHistoryEntityService;


@Controller
@RequestMapping(value = "/common/order")
public class OrderController {

  private static final int MAXRESULT = 20;
  private static final Logger LOGGER = LogManager.getLogger(OrderController.class);
  private static final String ACTIVE = "active";
  private static final String ORDER_ACTIVE_CLASS = "order_active_class";


  @Autowired
  private IUserTransactionHistoryEntityService userTransactionHistoryEntityService;
  @Autowired
  private IProductService productService;
  
  @RequestMapping(value = "/manage", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ModelAndView manage(HttpSession session, @RequestParam(required = false) String status,
      @RequestParam(required = false) Integer pageNum) {
    ModelAndView modelAndView = new ModelAndView();
    Map<String, Object> data = new HashMap<String, Object>();
    if (pageNum == null || pageNum <= 0) {
      pageNum = 1;
    }

    data.put(ORDER_ACTIVE_CLASS, ACTIVE);
   
     
    List<CheckoutDto> orderList =userTransactionHistoryEntityService.getOrderListWithStatus(status, pageNum, MAXRESULT);
//    String refererHeader = request.getHeader("Referer");
    /*if ("success".equals(session.getAttribute("editUserGroup"))) {
      data.put("message", "edituser.success");
      session.removeAttribute("editUserGroup");
    }*/

    if(status == null){
    	status ="All";
    }
    data.put("status", status);
    data.put("orderList", orderList);
//    data.put("pageNum", pageNum);
//    data.put("totalPages", 1);
    modelAndView.addAllObjects(data);
    modelAndView.setViewName("manageorder");
    return modelAndView;
  }

  @RequestMapping(method = RequestMethod.GET, value = "/allproducts", produces = MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView allProducts(@RequestParam(required = false) Integer pageNum) {
	  ModelAndView modelAndView = new ModelAndView(); 
	  if (pageNum == null || pageNum <= 0) {
	      pageNum = 1;
	    }
		Map<String, Object> responseMap = new HashMap<>();
		List<ProductDto> productDtoList = productService.allProducts(pageNum, 100);
		responseMap.put("success", Boolean.TRUE);
		responseMap.put("productList", productDtoList);
		modelAndView.addAllObjects(responseMap);
		modelAndView.setViewName("allproducts");
		return modelAndView;
	}

  @RequestMapping(value = "/manages", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody  Map<String, Object> manages(HttpSession session, @RequestParam(required = false) String status,
      @RequestParam(required = false) Integer pageNum) {
    Map<String, Object> data = new HashMap<String, Object>();
    if (pageNum == null || pageNum <= 0) {
      pageNum = 1;
    }

    data.put(ORDER_ACTIVE_CLASS, ACTIVE);
   
     
    List<CheckoutDto> orderList =userTransactionHistoryEntityService.getOrderListWithStatus(status, pageNum, MAXRESULT);
//    String refererHeader = request.getHeader("Referer");
    /*if ("success".equals(session.getAttribute("editUserGroup"))) {
      data.put("message", "edituser.success");
      session.removeAttribute("editUserGroup");
    }*/

    if(status == null){
    	status ="All";
    }
    data.put("status", status);
    data.put("orderList", orderList);
    return data;
//    data.put("pageNum", pageNum);
//    data.put("totalPages", 1);
//    modelAndView.addAllObjects(data);
//    modelAndView.addObject("UserDto", new UserDto());
//    modelAndView.setViewName("manageuser");
//    return modelAndView;
  }

  @RequestMapping(value = "/delete/{orderId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Map<String, Object> deleteOrder(@PathVariable int orderId, @RequestParam Boolean isDeleteAll) {
    Map<String, Object> data = new HashMap<String, Object>();
    try{
    	userTransactionHistoryEntityService.deleteOrder(orderId, isDeleteAll);
    	data.put("success", true);
    }catch(NullPointerException | IllegalArgumentException | InvalidDataAccessApiUsageException e){
        LOGGER.error("Error : ",e);
        data.put("success", false);
        if(!isDeleteAll){
        data.put("message","Entity does not exist");
        }
    }
    catch(Exception e){
        data.put("success", false); 
        LOGGER.error("Error : ",e);
    }
    return data;
  }
}
