package com.auth;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public class RestAuthFilter implements Filter {
  @Autowired
  private MessageSource messageSource;

  @Override
  public void destroy() {
    // TODO Auto-generated method stub

  }

  @Override
  public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
    // TODO Auto-generated method stub
    HttpServletRequest request = (HttpServletRequest) arg0;
    HttpServletResponse response = (HttpServletResponse) arg1;
    String header = request.getHeader("Authorization");

    if (header == null || !header.startsWith("Basic ")) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType("application/json");
      PrintWriter writer = response.getWriter();
      writer.println("{\"success\":false,\"message\":\"" + messageSource.getMessage("NotBlank.logindetail", null, Locale.ENGLISH) + "\"}");
      return;
    } else {
      arg2.doFilter(arg0, arg1);
    }
  }

  @Override
  public void init(FilterConfig arg0) throws ServletException {
    // TODO Auto-generated method stub

  }

}
