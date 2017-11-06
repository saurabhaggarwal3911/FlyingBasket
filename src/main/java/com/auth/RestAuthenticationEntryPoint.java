package com.auth;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

public class RestAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

  @Autowired
  private MessageSource messageSource;

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException,
      ServletException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType("application/json");
    PrintWriter writer = response.getWriter();
    writer.println("{\"success\":false,\"message\":\"" + messageSource.getMessage("login.notsuccess", null, Locale.ENGLISH) + "\"}");
  }

}
