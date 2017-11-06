package com.utility;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.dto.CurrentUserDto;

@Component
@Order(1)
public class TenantFilter implements Filter {
  private static final Logger LOGGER = LogManager.getLogger(TenantFilter.class);

  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    String path = request.getRequestURI().substring(request.getContextPath().length());
    if (path.indexOf("/public/") != -1 || path.indexOf("/WEB-INF/") != -1) {
      chain.doFilter(request, response);
    } else {
      long startTime = System.currentTimeMillis();
      requestStartLog(request);

      response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
      response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
      response.setDateHeader("Expires", 0); // Proxies.
      try {
        chain.doFilter(request, response);
      } catch (IOException e) {

        logError(request, e);
        throw new IOException(e);
      } catch (Exception e) {
        logError(request, e);
        throw new ServletException(e);
      } finally {
        Tenant.clearCurrentTenant();
        long endtime = System.currentTimeMillis();
        long totalTime = endtime-startTime;
        requestEndLog(request, totalTime);
      }
    }
  }

  
  private void requestStartLog(HttpServletRequest request) {
    Map<String, String[]> parameterMap = request.getParameterMap();
    StringBuilder stringBuilder = new StringBuilder();
    if (parameterMap != null) {
      for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
        stringBuilder.append("-");
        stringBuilder.append(entry.getKey());
        stringBuilder.append(":");
        Object[] value = entry.getValue();
        if (value != null) {
          for (int i = 0; i < value.length; i++) {
            if (i != 0) {
              stringBuilder.append(",");
            }
            Object object = value[i];
            stringBuilder.append(object.toString());
          }
        }
      }
    }
    CurrentUserDto currentUserDto = null;
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof CurrentUserDto) {
      currentUserDto = (CurrentUserDto) authentication.getPrincipal();
    }
    LOGGER.info("request start time "+new Date()+" to url " + request.getRequestURL() + " with parameter- " + stringBuilder+" by user: "+currentUserDto);
    GeneralUtil.printMemory();
  }
  
  private void requestEndLog(HttpServletRequest request, long totalTime) {
    Map<String, String[]> parameterMap = request.getParameterMap();
    StringBuilder stringBuilder = new StringBuilder();
    if (parameterMap != null) {
      for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
        stringBuilder.append("-");
        stringBuilder.append(entry.getKey());
        stringBuilder.append(":");
        Object[] value = entry.getValue();
        if (value != null) {
          for (int i = 0; i < value.length; i++) {
            if (i != 0) {
              stringBuilder.append(",");
            }
            Object object = value[i];
            stringBuilder.append(object.toString());
          }
        }
      }
    }
    CurrentUserDto currentUserDto = null;
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof CurrentUserDto) {
      currentUserDto = (CurrentUserDto) authentication.getPrincipal();
    }
    LOGGER.info("request end time "+new Date()+" with total time "+totalTime+" in ms to url " + request.getRequestURL() + " with parameter- " + stringBuilder+" by user: "+currentUserDto);
    GeneralUtil.printMemory();
  }
  
  private void logError(HttpServletRequest request, Exception e) {
    Map<String, String[]> parameterMap = request.getParameterMap();
    StringBuilder stringBuilder = new StringBuilder();
    if (parameterMap != null) {
      for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
        stringBuilder.append("-");
        stringBuilder.append(entry.getKey());
        stringBuilder.append(":");
        Object[] value = entry.getValue();
        if (value != null) {
          for (int i = 0; i < value.length; i++) {
            if (i != 0) {
              stringBuilder.append(",");
            }
            Object object = value[i];
            stringBuilder.append(object.toString());
          }
        }
      }
    }
    CurrentUserDto currentUserDto = null;
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof CurrentUserDto) {
      currentUserDto = (CurrentUserDto) authentication.getPrincipal();
    }
    LOGGER.error("Error at "+new Date()+" in " + request.getRequestURL() + " with parameter- " + stringBuilder+" by user: "+currentUserDto, e);
  }

  @Override
  public void destroy() {
    // destroy

  }

  @Override
  public void init(FilterConfig arg0) throws ServletException {
    // init

  }
}
