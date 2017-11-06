package com.config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class ConfigurationEmbeddedTomcat {

  @Bean
  public EmbeddedServletContainerCustomizer containerCustomizer() {
    return new EmbeddedServletContainerCustomizer() {
      @Override
      public void customize(ConfigurableEmbeddedServletContainer container) {
        // TomcatEmbeddedServletContainerFactory tomcat = (TomcatEmbeddedServletContainerFactory)
        // container;
        // tomcat.setSessionTimeout(1, TimeUnit.MINUTES);
        ErrorPage error400Page = new ErrorPage(HttpStatus.BAD_REQUEST, "/WEB-INF/tiles/view/badRequestError.jsp");
        ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/WEB-INF/tiles/view/pagenotfound.jsp");
        ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/WEB-INF/tiles/view/internalServerError.jsp");
        container.addErrorPages(error400Page, error404Page, error500Page);
      }
    };
  }
}
