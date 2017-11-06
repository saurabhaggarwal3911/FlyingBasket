package com.auth;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

public class MyAccessDeniedHandler implements AccessDeniedHandler {
  private Map<String, String> accessDeniedPageMap;
  private String defaultAccessDeniedPage;

  public MyAccessDeniedHandler() {}


  public Map<String, String> getAccessDeniedPageMap() {
    return accessDeniedPageMap;
  }

  public void setAccessDeniedPageMap(Map<String, String> accessDeniedPageMap) {
    this.accessDeniedPageMap = accessDeniedPageMap;
  }



  public String getDefaultAccessDeniedPage() {
    return defaultAccessDeniedPage;
  }

  public void setDefaultAccessDeniedPage(String defaultAccessDeniedPage) {
    this.defaultAccessDeniedPage = defaultAccessDeniedPage;
  }

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException,
      ServletException {
    // do some business logic, then redirect to errorPage url
    Set<String> roles = AuthorityUtils.authorityListToSet(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
    String accessDeniedPage = null;
    outerloop: for (String role : roles) {
      Iterator<Entry<String, String>> iterator = accessDeniedPageMap.entrySet().iterator();
      while (iterator.hasNext()) {
        Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
        String key = entry.getKey();
        String value = entry.getValue();
        if (key.equals(role)) {
          accessDeniedPage = value;
          break outerloop;
        }
      }
    }
    if (accessDeniedPage == null) {
      SecurityContextHolder.clearContext();
      accessDeniedPage = defaultAccessDeniedPage;
    }

    response.sendRedirect(request.getContextPath() + accessDeniedPage);

  }

}
