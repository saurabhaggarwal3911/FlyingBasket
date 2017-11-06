package com.auth;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.dto.CurrentUserDto;

public class MyAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
  private List<String> hitIncrementRoleList;
  private static final int COUNT = 0;
  private String targetUrlForSiteAdmin;
  private String targetUrlForNormalUser;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException,
      ServletException {


    final CurrentUserDto currentUserDto = (CurrentUserDto) authentication.getPrincipal();
    Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
    boolean isRoleExist = false;
    for (String role : roles) {
      if (hitIncrementRoleList.contains(role)) {
        isRoleExist = true;
        break;
      }
    }
    if (isRoleExist) {/*

      Thread thread = new Thread() {
        @Override
        public void run() {

          UserHitsDto userHitsDto = new UserHitsDto();
          userHitsDto.setAccessType(ApplicationConstants.AccessTypes.DESKTOP);
          userHitsDto.setTocCode(currentUserDto.getTocCode());
          userHitsDto.setUserId(currentUserDto.getId());
          userHitsService.incrementHits(userHitsDto);
        }
      };
      thread.start();
    */}

    // This is used to show welcome user
    request.getSession().setAttribute("count", COUNT);
   
    
   /* if (roles.contains(hitIncrementRoleList.get(2))) {
//        super.onAuthenticationSuccess(request, response, authentication);
      } else if (roles.contains("ROLE_USER")) {
//        getRedirectStrategy().sendRedirect(request, response, getTargetUrlForNormalUser());
      }
      else */if (roles.contains("ROLE_SITE_ADMIN")){
        getRedirectStrategy().sendRedirect(request, response, getTargetUrlForSiteAdmin());
      }else{
    	  super.onAuthenticationSuccess(request, response, authentication);
      }
  }



  public List<String> getHitIncrementRoleList() {
    return hitIncrementRoleList;
  }

  public void setHitIncrementRoleList(List<String> hitIncrementRoleList) {
    this.hitIncrementRoleList = hitIncrementRoleList;
  }


  public String getTargetUrlForSiteAdmin() {
    return targetUrlForSiteAdmin;
  }


  public void setTargetUrlForSiteAdmin(String targetUrlForSiteAdmin) {
    this.targetUrlForSiteAdmin = targetUrlForSiteAdmin;
  }

  
  public String getTargetUrlForNormalUser() {
    return targetUrlForNormalUser;
  }


  public void setTargetUrlForNormalUser(String targetUrlForNormalUser) {
    this.targetUrlForNormalUser = targetUrlForNormalUser;
  }

}
