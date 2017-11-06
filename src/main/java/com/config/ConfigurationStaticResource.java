package com.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ConfigurationStaticResource extends WebMvcConfigurerAdapter {


  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/public/**").addResourceLocations("/public/").setCachePeriod(3600 * 24 * 7);
  }

}
