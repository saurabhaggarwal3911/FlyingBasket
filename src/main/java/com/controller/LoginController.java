package com.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.service.IUserEntityService;
import com.utility.EmailValidator;


@Controller
public class LoginController {
  private static final Logger LOGGER = LogManager.getLogger(LoginController.class);
  @Autowired
  private IUserEntityService userEntityService;


  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public ModelAndView login(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "error", required = false) String error,
      @RequestParam(value = "logout", required = false) String logout, @RequestParam(value = "errordenied", required = false) String errordenied) {
    ModelAndView modelAndView = new ModelAndView();

    if (request.getSession().getAttribute("SPRING_SECURITY_CONTEXT") != null) {
//        if(GeneralUtil.getRoleId()==5){
//        	securityUtil.logoutManully(GeneralUtil.getUserId());
//        }else{
        	modelAndView.setViewName("redirect:/common/user/manage");
//        }
//        else if(GeneralUtil.getRoleId()==2){
//          modelAndView.setViewName("redirect:/userAudit/myaudits/manage");
//            }
      return modelAndView;
    }
    try {
      setErrorCodeInResponseThroughAjax(request, response, 401);
    } catch (IOException e) {
      LOGGER.error("Excepion in login method", e);
    }
    modelAndView.setViewName("loginpage");

    
    if (error != null) {
      modelAndView.addObject("error", "login.notsuccess");
    } else if (logout != null) {
      modelAndView.addObject("logout", "logout.success");
    } else if (errordenied != null) {
      modelAndView.addObject("error", "login.accessdenied");
    }

    return modelAndView;
  }

  @RequestMapping(value = "/", method = RequestMethod.GET)
  private ModelAndView redirectLogin() {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("redirect:/login");
    return modelAndView;
  }

  public void setErrorCodeInResponseThroughAjax(HttpServletRequest request, HttpServletResponse response, int code) throws IOException {
    String ajaxHeader = ((HttpServletRequest) request).getHeader("X-Requested-With");

    if ("XMLHttpRequest".equals(ajaxHeader)) {
      // logger.info("Ajax call detected, send {} error code :"+this.customSessionExpiredErrorCode);
      HttpServletResponse resp = (HttpServletResponse) response;
      resp.sendError(code);
    }
  }

  @RequestMapping(value = "/accessdenied", method = RequestMethod.GET)
  private ModelAndView accessDenied() {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("accessDenied");

    return modelAndView;
  }

  @RequestMapping(value = "/invalidsession", method = RequestMethod.GET)
  private ModelAndView invalidSession(HttpServletRequest request, HttpServletResponse response) {
    ModelAndView modelAndView = new ModelAndView();
    if (request.getSession().getAttribute("SPRING_SECURITY_CONTEXT") != null) {
//    	if(GeneralUtil.getRoleId()==3){
//    		modelAndView.setViewName("redirect:/workspace/product/manage");
//    	}else if(GeneralUtil.getRoleId()==1){
    		modelAndView.setViewName("redirect:/common/user/manage");
//    	}
      return modelAndView;
    }
    modelAndView.setViewName("invalidSession");
    try {
      setErrorCodeInResponseThroughAjax(request, response, 901);
    } catch (IOException e) {
      LOGGER.error("Exception in invalidsession", e);
    }
    return modelAndView;
  }

  @RequestMapping(value = "/forgotpassword", method = RequestMethod.GET)
  public ModelAndView forgotPassword(HttpServletRequest request) {
    ModelAndView modelAndView = new ModelAndView();
    if (request.getSession().getAttribute("SPRING_SECURITY_CONTEXT") != null) {
//    	if(GeneralUtil.getRoleId()==3){
//    		modelAndView.setViewName("redirect:/workspace/product/manage");
//    	}else if(GeneralUtil.getRoleId()==1){
    		modelAndView.setViewName("redirect:/common/user/manage");
//    	}

      return modelAndView;
    }
    modelAndView.setViewName("forgotPassword");

    return modelAndView;
  }

  @RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
  public ModelAndView forgotPassword(HttpServletRequest request, @RequestParam(required = true) String username) {
    ModelAndView modelAndView = new ModelAndView();
    Map<String, Object> data = new HashMap<>();
    if (request.getSession().getAttribute("SPRING_SECURITY_CONTEXT") != null) {
//    	if(GeneralUtil.getRoleId()==3){
//    		modelAndView.setViewName("redirect:/workspace/product/manage");
//    	}else if(GeneralUtil.getRoleId()==1){
    		modelAndView.setViewName("redirect:/common/user/manage");
//    	}

      return modelAndView;
    }
    boolean validate = new EmailValidator().validate(username);
    if(!validate){
    	validate = Pattern.compile("\\d{10}").matcher(username).matches();
    }
    if (validate) {
    	String handleForgotPassword = userEntityService.handleForgotPassword(username);
      switch (handleForgotPassword) {
        case "userNotFound":
          data.put("error", "forgotpassword.wrongemail");
          break;
        case "accountDisabled":
          data.put("error", "forgotpassword.inactiveemail");
          break;
        case "mailError":
          data.put("error", "forgotpassword.mailerror");
          break;
        case "success":
          data.put("success", "forgotpassword.success");
          break;
        default:
          break;
      }
    } else {
      data.put("error", "forgotpassowrd.emailnotvalid");
    }
    data.put("username", username);
    modelAndView.addAllObjects(data);
    modelAndView.setViewName("forgotPassword");
    return modelAndView;
  }
}
