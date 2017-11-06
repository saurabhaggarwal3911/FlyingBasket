package com.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dto.ChangePasswordDto;
import com.dto.CurrentUserDto;
import com.dto.DeviceInformationDto;
import com.dto.FieldErrorDTO;
import com.dto.ForgotPasswordDto;
import com.dto.LoginDto;
import com.dto.RestUserDto;
import com.dto.UserInformationDto;
import com.dto.UserProfileDto;
import com.exception.DataNotExistException;
import com.exception.UserAlreadyExistException;
import com.exception.UserEmailAlreadyExistException;
import com.exception.UserMobileAlreadyExistException;
import com.service.IApplicationConfigurationService;
import com.service.IDeviceInformationEntityService;
import com.service.IUserEntityService;
import com.utility.ApplicationConstants;
import com.utility.Tenant;

@RestController
@RequestMapping(value = "/rs/user")
public class RestUserController {
	
	 private static final Logger LOGGER = LogManager.getLogger(RestUserController.class);
	 
	 
  @Autowired
  private IUserEntityService userEntityService;
  @Autowired
  private MessageSource messageSource;
  @Autowired
  private IDeviceInformationEntityService deviceInformationEntityService;
  @Autowired
  private IApplicationConfigurationService applicationConfigurationService;

  @RequestMapping(value = "/changepassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Map<String, Object> changePassword(@RequestBody @Valid ChangePasswordDto changePasswordDto) {
    Map<String, Object> responseMap = new HashMap<String, Object>();
    CurrentUserDto currentUserDto = (CurrentUserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    changePasswordDto.setUserId(currentUserDto.getUsername());
    boolean success = userEntityService.changePassword(changePasswordDto);
    responseMap.put("success", success);
    if (success) {
      responseMap.put("message", messageSource.getMessage("changePasswordDto.success", null, Locale.ENGLISH));
      responseMap.put("userId", changePasswordDto.getUserId());
      responseMap.put("password", changePasswordDto.getNewPassword());
    } else {
      responseMap.put("message", messageSource.getMessage("Worng.changePasswordDto.oldPassword", null, Locale.ENGLISH));
    }

    return responseMap;
  }


 
  @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Map<String, Object> login(@RequestBody @Valid final LoginDto loginDto) {
    Map<String, Object> responseMap = new HashMap<String, Object>();
    CurrentUserDto currentUserDto = (CurrentUserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    final int userId = currentUserDto.getId();
    UserInformationDto userInformationDto = new UserInformationDto();
    userInformationDto.setEmail(currentUserDto.getUsername());
    userInformationDto.setName(currentUserDto.getName());
    userInformationDto.setUserId(currentUserDto.getId()+"");
    userInformationDto.setValid(currentUserDto.isEnabled());
    responseMap.put("data", userInformationDto);
    responseMap.put("success", Boolean.TRUE);
    loginDto.setUserId(userId);
    Thread thread1 = new MyThread(loginDto);
    thread1.start();
    return responseMap;
  }

  @RequestMapping(method = RequestMethod.GET, value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody Map<String, Object> userProfile() {
    Map<String, Object> responseMap = new HashMap<>();
    CurrentUserDto currentUserDto = (CurrentUserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    UserProfileDto userProfileDto = userEntityService.getUserProfile(currentUserDto, false, false);
    LOGGER.info("userprofile an service called with response:-"+userProfileDto);
    responseMap.put("success", Boolean.TRUE);
    responseMap.put("data", userProfileDto);
    return responseMap;
  }
  @RequestMapping(value = "/updatable", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Map<String, Object> updatable(@RequestBody @Valid LoginDto loginDto) {
    Map<String, Object> responseMap = new HashMap<String, Object>();
    loginDto = applicationConfigurationService.updatable(loginDto);
    if(loginDto.isUpdatable()){
    	responseMap.put("data", loginDto);
        responseMap.put("success", Boolean.TRUE);
    }else{
        responseMap.put("success", Boolean.FALSE);
    }
    
    return responseMap;
  }
  @RequestMapping(method = RequestMethod.POST, value = "/forgotpassword")
  public Map<String, Object> forgotPassword(@RequestBody @Valid ForgotPasswordDto forgotPasswordDto) {
    Map<String, Object> responseMap = new HashMap<>();
    String handleForgotPassword = userEntityService.handleForgotPassword(forgotPasswordDto.getEmail());
    switch (handleForgotPassword) {
      case "userNotFound":
        responseMap.put(ApplicationConstants.SUCCESS, Boolean.FALSE);
        responseMap.put("message", messageSource.getMessage("forgotpassword.wrongemail", null, Locale.ENGLISH));
        break;
      case "accountDisabled":
        responseMap.put(ApplicationConstants.SUCCESS, Boolean.FALSE);
        responseMap.put("message", messageSource.getMessage("forgotpassword.inactiveemail", null, Locale.ENGLISH));
        break;
      case "mailError":
        responseMap.put(ApplicationConstants.SUCCESS, Boolean.FALSE);
        responseMap.put("message", messageSource.getMessage("forgotpassword.mailerror", null, Locale.ENGLISH));
        break;
      case ApplicationConstants.SUCCESS:
        responseMap.put(ApplicationConstants.SUCCESS, Boolean.TRUE);
        responseMap.put("message", messageSource.getMessage("forgotpassword.success", null, Locale.ENGLISH));
        break;
      default:
        break;
    }
    return responseMap;
  }
  
  @RequestMapping(method = RequestMethod.POST, value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Map<String, Object> logout(@RequestBody LoginDto loginDto) {
    Map<String, Object> responseMap = new HashMap<>();
    deviceInformationEntityService.deleteRegIdbyDeviceId(loginDto.getDeviceId());
    responseMap.put("success", Boolean.TRUE);
    return responseMap;
  }
  
  @RequestMapping(method = RequestMethod.GET, value = "/refer", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Map<String, Object> sendSMS(@RequestParam(required = true) String mobile) {
    Map<String, Object> responseMap = new HashMap<>();
    try {
		userEntityService.sendSMS(mobile);
		responseMap.put("success", Boolean.TRUE);
	} catch (IOException e) {
		responseMap.put("success", Boolean.FALSE);
		LOGGER.info("error while sending refer sms "+e);
	}
    return responseMap;
  }
  
  @RequestMapping(method = RequestMethod.POST, value = "/signup")
  public Map<String, Object> signup(@RequestBody @Valid RestUserDto restUserDto, Errors errors) {
	  LOGGER.info("signup an service called with data:-"+restUserDto);

	  Map<String, Object> responseMap = new HashMap<>();
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
		  return responseMap;
	  }
	  if (!hasError) {
		  try {
			  restUserDto.setRole(ApplicationConstants.Roles.USER);
			  restUserDto.setValid(true);
	    	  userEntityService.addRestUser(restUserDto);
	    	  responseMap.put("success", Boolean.TRUE);
	    	  
	    	  responseMap.put("message", messageSource.getMessage("adduser.success", null, Locale.ENGLISH));
			  LOGGER.info("signup an service called with responseMap:-"+responseMap);

	    	  return responseMap;
		  } catch (UserMobileAlreadyExistException e) {
			  FieldErrorDTO error = new FieldErrorDTO("mobile", messageSource.getMessage("adduser.mobilereadyexist", null, Locale.ENGLISH));
			  fieldErrorDTOs.add(error);
		  } catch (UserEmailAlreadyExistException e) {
			  FieldErrorDTO error = new FieldErrorDTO("email", messageSource.getMessage("adduser.emailalreadyexist", null, Locale.ENGLISH));
			  fieldErrorDTOs.add(error);
	      } catch (UserAlreadyExistException e) {
	    	  FieldErrorDTO error = new FieldErrorDTO("mobile", messageSource.getMessage("adduser.mobilereadyexist", null, Locale.ENGLISH));
			  FieldErrorDTO error1 = new FieldErrorDTO("email", messageSource.getMessage("adduser.emailalreadyexist", null, Locale.ENGLISH));
			  fieldErrorDTOs.add(error);
			  fieldErrorDTOs.add(error1);
		  }catch (DataNotExistException e) {
			  FieldErrorDTO error = new FieldErrorDTO("referralCode", messageSource.getMessage("adduser.referralcodenotexist", null, Locale.ENGLISH));
			  fieldErrorDTOs.add(error);
		  }
		  catch (Exception e) {
			  responseMap.put("message",messageSource.getMessage("adduser.error", null, Locale.ENGLISH));
		  }
	    }
	  	responseMap.put("fieldErrors", fieldErrorDTOs);
	  	responseMap.put("success", Boolean.FALSE);
	  	LOGGER.info("signup an service called with responseMap:-"+responseMap);

	    return responseMap;
  }
 
  private class MyThread extends Thread {

    LoginDto loginDto;

    public MyThread(LoginDto loginDto) {
      this.loginDto = loginDto;
    }

    @Override
    public void run() {
      DeviceInformationDto deviceInformationDto = new DeviceInformationDto();
      deviceInformationDto.setDeviceId(loginDto.getDeviceId());
      deviceInformationDto.setRegId(loginDto.getRegId());
      deviceInformationDto.setUserId(loginDto.getUserId());
      deviceInformationDto.setVersionNum(loginDto.getVersionNum());
      deviceInformationEntityService.saveOrGetDeviceInfoByDeviceId(deviceInformationDto);
      Tenant.clearCurrentTenant();
    }
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
