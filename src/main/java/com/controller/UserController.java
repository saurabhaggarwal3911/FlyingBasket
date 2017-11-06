package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dto.ChangePasswordDto;
import com.dto.CurrentUserDto;
import com.dto.NameIdDto;
import com.dto.UserDto;
import com.dto.UserProfileDto;
import com.exception.UserAlreadyExistException;
import com.exception.UserCanNotRemoveFromPoolException;
import com.exception.UserEmailAlreadyExistException;
import com.exception.UserMobileAlreadyExistException;
import com.service.IRoleService;
import com.service.IUserEntityService;
import com.utility.ApplicationConstants;

@Controller
@RequestMapping(value = "/common/user")
public class UserController {

  private static final int MAXRESULT = 20;
  private static final Logger LOGGER = LogManager.getLogger(UserController.class);
  private static final String ACTIVE = "active";
  private static final String USER_ACTIVE_CLASS = "user_active_class";
  private static final String ALL_USER_ACTIVE_TAB = "all_user_active_tab";

  @Autowired
  private MessageSource messageSource;
  @Autowired
  private IUserEntityService userEntityService;
  @Autowired
  private IRoleService roleService;

  @RequestMapping(value = "/manage", method = RequestMethod.GET)
  public ModelAndView manage(HttpSession session, @RequestParam(required = false) String userType,
      @RequestParam(required = false) Integer pageNum) {
    ModelAndView modelAndView = new ModelAndView();
    Map<String, Object> data = new HashMap<String, Object>();
    if (pageNum == null || pageNum <= 0) {
      pageNum = 1;
    }

    data.put(USER_ACTIVE_CLASS, ACTIVE);
    
   /* if(StringUtils.isEmpty(userType)){
      Integer clientId = GeneralUtil.getClientId();
      Integer workspaceId = GeneralUtil.getWorkspaceId();
      if(clientId != null){
          userType = "manualUser";
      }else if(workspaceId != null){
          userType = "workspaceUser";
      }else{
          userType = "manualUser";
      }
  }*/
    
   

   /* if ("workspaceUser".equals(userType)) {
      data.put(WORKSPACE_USER_ACTIVE_TAB, ACTIVE);
    } else if ("manualUser".equals(userType)) {
      data.put(MANUAL_USER_ACTIVE_CLASS, ACTIVE);
    } else if ("adUser".equals(userType)) {
      data.put(AD_USER_ACTIVE_CLASS, ACTIVE);
    } else if ("allUser".equals(userType)) {*/
      data.put(ALL_USER_ACTIVE_TAB, ACTIVE);
//    }

    List<UserDto> userList = userEntityService.getUserList(userType, pageNum, MAXRESULT);
//    String refererHeader = request.getHeader("Referer");
    if ("success".equals(session.getAttribute("editUserGroup"))) {
      data.put("message", "edituser.success");
      session.removeAttribute("editUserGroup");
    }


    data.put("userList", userList);
    data.put("userType", "orgnaizationUser");
    data.put("pageNum", pageNum);
    data.put("totalPages", userEntityService.getCount(MAXRESULT, userType));
    modelAndView.addAllObjects(data);
    modelAndView.addObject("UserDto", new UserDto());
    modelAndView.setViewName("manageuser");
    return modelAndView;
  }

  /*@RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public List<NameIdDto> searchUser(@RequestParam String userType, @RequestParam String query) {
    return userEntityService.searchUser(userType, query);
  }*/

  @RequestMapping(value = "/add", method = RequestMethod.GET)
  public ModelAndView add() {
    ModelAndView modelAndView = new ModelAndView();
    Map<String, Object> data = new HashMap<String, Object>();
    data.put(USER_ACTIVE_CLASS, ACTIVE);
    modelAndView.addObject("userDto", new UserDto());
    modelAndView.addAllObjects(data);
    modelAndView.setViewName("adduser");
    return modelAndView;
  }

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public ModelAndView addUser(@ModelAttribute UserDto userDto, Errors errors) {
    ModelAndView modelAndView = new ModelAndView();
    boolean hasError = false;
    if (errors.hasErrors()) {
      hasError = true;
    }
    if (!hasError) {
      try {
        if(userDto.getRole()==0){
          userDto.setRole(ApplicationConstants.Roles.SITE_ADMIN);
        }
        userDto.setValid(true);
        userDto.setEmail(userDto.getEmail().toLowerCase());
        userEntityService.addUser(userDto);
        modelAndView.addObject("success", messageSource.getMessage("adduser.success", null, Locale.ENGLISH));
      } catch (UserAlreadyExistException e) {
        LOGGER.error(e);
        errors.rejectValue("email", "adduser.emailalreadyexist");
        errors.rejectValue("mobile", "adduser.emailalreadyexist");
      }catch (UserMobileAlreadyExistException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (UserEmailAlreadyExistException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (Exception e) {
        LOGGER.error(e);
        modelAndView.addObject("error",messageSource.getMessage("adduser.error", null, Locale.ENGLISH) );
      } 
    }

    Map<String, Object> data = new HashMap<String, Object>();
    data.put("type", "Add");
    data.put(USER_ACTIVE_CLASS, ACTIVE);
    data.put("userDto", userDto);
    modelAndView.addAllObjects(data);
    modelAndView.setViewName("adduser");
    return modelAndView;
  }

  @RequestMapping(value = "/profile", method = RequestMethod.GET)
  public ModelAndView profile() {
    ModelAndView modelAndView = new ModelAndView();
    Map<String, Object> data = new HashMap<>();
    data.put("changePasswordDto", new ChangePasswordDto());
    modelAndView.addAllObjects(data);
    // get logged in username
    CurrentUserDto currentUserDto = (CurrentUserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    String name = com.auth.getName();
    UserProfileDto userProfileDto = userEntityService.getUserProfile(currentUserDto, false, false);
    
//    UserDto userDto = userEntityService.getUserByEmail(name);
//    List<WorkspaceEntity> workspaceList = userDto.getWorkspaceList();
    modelAndView.addObject("userProfileDto", userProfileDto);
    // modelAndView.addObject("userDto",userDto);
    modelAndView.setViewName("profile");
    return modelAndView;

  }

  @RequestMapping(value = "/profile", method = RequestMethod.POST)
  public ModelAndView changepassword(@ModelAttribute @Valid ChangePasswordDto changePasswordDto, Errors errors) {
    ModelAndView modelAndView = new ModelAndView();
    Map<String, Object> data = new HashMap<>();
    boolean hasError = false;
    if (errors.hasErrors()) {
      hasError = true;
    }
    CurrentUserDto currentUserDto = (CurrentUserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (!hasError) {
      

        changePasswordDto.setUserId(currentUserDto.getUsername());
        boolean success = userEntityService.changePassword(changePasswordDto);
        if (success) {
          data.put("success", "changePasswordDto.success");
        } else {
          errors.rejectValue("oldPassword", "Worng.changePasswordDto.oldPassword");
        }

    }

    // get logged in username
//    Authentication com.auth = SecurityContextHolder.getContext().getAuthentication();
//    String name = com.auth.getName();
//    UserDto userDto = userEntityService.getUserByEmail(name);
//    List<WorkspaceEntity> workspaceList = userDto.getWorkspaceList();
    UserProfileDto userProfileDto = userEntityService.getUserProfile(currentUserDto, false, false);
    modelAndView.addObject("userProfileDto", userProfileDto);
    // modelAndView.addObject("userDto",userDto);

    data.put("changePasswordDto", changePasswordDto);
    modelAndView.addAllObjects(data);
    modelAndView.setViewName("profile");
    return modelAndView;
  }

  @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
  public ModelAndView edit(@PathVariable int id) {
    
    

    ModelAndView modelAndView = new ModelAndView();
    Map<String, Object> data = new HashMap<String, Object>();
    /*boolean isSiteAdmin = false;
    boolean isClientAdmin = false;
    boolean isWorkspaceAdmin = false;*/
    
    /*if(clientId != null){
        isClientAdmin = true;
        data.put("workspaceList", workspaceService.getActiveWorkspaceListByClientId());
    }else if(workspaceId != null){
        isWorkspaceAdmin = true;
        BrandDto workspaceDto = workspaceService.getWorkspaceById(workspaceId, false, false);
        data.put("workspaceName", workspaceDto.getName() );
    }else{
        isSiteAdmin= true;
    }*/
    
    UserDto userById = userEntityService.getUserById(id);
    data.put("userRole", roleService.getRoleById(userById.getRole()));
    data.put("userDto", userById);
    
    data.put(USER_ACTIVE_CLASS, ACTIVE);
//    data.put("oldUserName", userById.());
    modelAndView.addAllObjects(data);
    modelAndView.setViewName("edituser");
    return modelAndView;
  }

  @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
  public ModelAndView editUser(@PathVariable int id, @ModelAttribute UserDto userDto, Errors errors, @RequestParam String userInEditMode,
      @RequestParam(required = false) String oldUserName, HttpSession session) {
    ModelAndView modelAndView = new ModelAndView();
    boolean hasError = false;
    if (errors.hasErrors()) {
      hasError = true;
    }
    if (!hasError) {
      try {
        userDto.setId(id);
        userEntityService.editUser(userDto);
        modelAndView.setViewName("redirect:../manage");
        session.setAttribute("editUserGroup", "success");
        return modelAndView;
      } catch (UserCanNotRemoveFromPoolException e) {
        LOGGER.info(e.getMessage(), e);
        modelAndView.addObject("error", messageSource.getMessage("edituser.cannotremovefromclientpool", new String[] {e.getName()}, Locale.ENGLISH));
      } catch (UserAlreadyExistException e) {
        LOGGER.info("user already exist. ", e);
        errors.rejectValue("username", "edituser.emailalreadyexist");
      } catch (Exception e) {
        LOGGER.error("Exception in editUser ", e);
        modelAndView.addObject("error", messageSource.getMessage("edituser.error", null, Locale.ENGLISH));
      }
    }
    Map<String, Object> data = new HashMap<String, Object>();
    data.put("userDto", userDto);
    data.put(USER_ACTIVE_CLASS, ACTIVE);
    data.put("userRole", roleService.getRoleById(userDto.getRole()));
    data.put("oldUserName", oldUserName);
    data.put("userInEditMode", userInEditMode);
    modelAndView.addAllObjects(data);
    modelAndView.setViewName("edituser");
    return modelAndView;

  }
  
  @RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public List<NameIdDto> searchUser(@RequestParam String userType, @RequestParam String query) {
    return userEntityService.searchUser(userType, query);
  }

}
