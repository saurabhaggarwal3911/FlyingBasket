package com.auth;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

public class MyRestServiceAccessDeniedHandler implements AccessDeniedHandler {
  @Autowired
  private MessageSource messageSource;

  private String realmName;

  public MyRestServiceAccessDeniedHandler() {}


  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException,
      ServletException {
    // do some business logic, then redirect to errorPage url
    // response.addHeader("WWW-Authenticate", null);
    SecurityContextHolder.clearContext();
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType("application/json");
    // response.sendError(HttpServletResponse.SC_UNAUTHORIZED, accessDeniedException.getMessage());
    PrintWriter writer = response.getWriter();
    writer.println("{\"success\":false,\"message\":\"" + messageSource.getMessage("login.accessdenied", null, Locale.ENGLISH) + "\"}");

  }


  public String getRealmName() {
    return realmName;
  }


  public void setRealmName(String realmName) {
    this.realmName = realmName;
  }


}
