package com.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.auth.BasicAuthenticationFilter;
import com.auth.CustomDaoAuthenticationProvider;
import com.auth.DaoAuthenticationProvider;
import com.auth.MyAccessDeniedHandler;
import com.auth.MyAuthenticationSuccessHandler;
import com.auth.MyRestServiceAccessDeniedHandler;
import com.auth.RestAuthFilter;
import com.auth.RestAuthenticationEntryPoint;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, proxyTargetClass = true)
public class ConfigurationForSecurity extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authenticationProvider());
  }


  @Bean
  public SessionRegistry sessionRegistry() {
    return new SessionRegistryImpl();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    CustomDaoAuthenticationProvider daoAuthenticationProvider = new CustomDaoAuthenticationProvider();
    daoAuthenticationProvider.setUserDetailsService(userDetailsService);
    daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
    return daoAuthenticationProvider;
  }

  @Configuration
  @Order(1)
  public static class ApiWebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.antMatcher("/rs/**")
          .authorizeRequests()
          .antMatchers("/rs/user/forgotpassword").permitAll()
          .antMatchers("/rs/user/updatable").permitAll()
          .antMatchers("/rs/user/signup").permitAll()
          .antMatchers("/rs/public/**").permitAll()
          .antMatchers("/rs/mobile/checkout").hasAnyAuthority("ROLE_CLIENT_ADMIN", "ROLE_WORKSPACE_ADMIN", "ROLE_SITE_ADMIN", "ROLE_USER")
          .antMatchers("/rs/mobile/**").permitAll()
          .antMatchers("/rs/user/**").hasAnyAuthority("ROLE_CLIENT_ADMIN", "ROLE_WORKSPACE_ADMIN", "ROLE_SITE_ADMIN", "ROLE_USER","ROLE_DELIVERY_ADMIN")
          .antMatchers("/rs/delivery/**").hasAnyAuthority("ROLE_CLIENT_ADMIN", "ROLE_WORKSPACE_ADMIN", "ROLE_SITE_ADMIN", "ROLE_DELIVERY_ADMIN")
          .and()
          .httpBasic()
          .realmName("REST Realm")
          .authenticationEntryPoint(restAuthenticationEntryPoint())
          .and()
          .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          .and()
          /* http */.csrf()
          .disable()

          .addFilterBefore(new BasicAuthenticationFilter(authenticationManager()),
              org.springframework.security.web.authentication.www.BasicAuthenticationFilter.class)
          // .formLogin().disable()
          .exceptionHandling().accessDeniedHandler(myRestServiceAccessDeniedHandler());


    }

    @Bean
    public MyRestServiceAccessDeniedHandler myRestServiceAccessDeniedHandler() {
      MyRestServiceAccessDeniedHandler myRestServiceAccessDeniedHandler = new MyRestServiceAccessDeniedHandler();
      myRestServiceAccessDeniedHandler.setRealmName("REST Realm");
      return myRestServiceAccessDeniedHandler;
    }

    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
      RestAuthenticationEntryPoint myRestServiceAccessDeniedHandler = new RestAuthenticationEntryPoint();
      myRestServiceAccessDeniedHandler.setRealmName("REST Realm");
      return myRestServiceAccessDeniedHandler;
    }

    // @Bean
    // public BasicAuthenticationFilter basicAuthenticationFilter(){
    // CustomBasicAuthenticationFilter customBasicAuthenticationFilter = null;
    // try {
    // customBasicAuthenticationFilter = new
    // CustomBasicAuthenticationFilter(authenticationManager(), restAuthenticationEntryPoint());
    // } catch (Exception e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // // BasicAuthenticationFilter myRestServiceAccessDeniedHandler = new
    // BasicAuthenticationFilter();
    // //
    // myRestServiceAccessDeniedHandler.setAuthenticationEntryPoint(restAuthenticationEntryPoint());
    // // try {
    // // myRestServiceAccessDeniedHandler.setAuthenticationManager(authenticationManager());
    // // } catch (Exception e) {
    // // // TODO Auto-generated catch block
    // // e.printStackTrace();
    // // }
    // return customBasicAuthenticationFilter;
    // }

    @Bean
    public RestAuthFilter restAuthFilter() {
      RestAuthFilter restAuthFilter = new RestAuthFilter();
      return restAuthFilter;
    }

    @Bean
    public FilterRegistrationBean customApiAuthenticationFilterRegistration() {
      FilterRegistrationBean registration = new FilterRegistrationBean(restAuthFilter());
      // registration.setEnabled(false);
      registration.addUrlPatterns("/rs/user/**");
      registration.setOrder(Integer.MAX_VALUE);
      return registration;
    }
  }

  @Configuration
  @Order(2)
  public static class FormWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
      web.ignoring().antMatchers("/public/**");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception { 
    	http.authorizeRequests().antMatchers("/public/**")
        .permitAll()
        .antMatchers("/login**").permitAll()
        .antMatchers("/forgotpassword**").permitAll()
        .antMatchers("/common/order/allproducts**").permitAll()
        .antMatchers("/accessdenied**").permitAll()
        .antMatchers("/workspace/**").hasAnyAuthority("ROLE_WORKSPACE_ADMIN")
        .antMatchers("/client/**").hasAnyAuthority("ROLE_CLIENT_ADMIN")
        .antMatchers("/common/**").hasAnyAuthority("ROLE_SITE_ADMIN", "ROLE_WORKSPACE_ADMIN", "ROLE_CLIENT_ADMIN")
        .antMatchers("/product/**").permitAll()
        .antMatchers("/admin/**").hasAnyAuthority("ROLE_SITE_ADMIN", "ROLE_WORKSPACE_ADMIN", "ROLE_CLIENT_ADMIN")
        .and().formLogin().loginPage("/login").failureUrl("/login?error").loginProcessingUrl("/j_spring_security_check")
        .usernameParameter("username").passwordParameter("password").successHandler(authenticationSuccessHandler()).permitAll().and()

        .logout()

        .logoutSuccessUrl("/login?logout").logoutUrl("/j_spring_security_logout").invalidateHttpSession(true).permitAll().and().sessionManagement()
        .sessionFixation().changeSessionId().maximumSessions(1).sessionRegistry(sessionRegistry()).expiredUrl("/invalidsession").and()
        .and().csrf()
        .disable().exceptionHandling().accessDeniedHandler(myAccessDeniedHandler());
    }

    @Bean
    public SessionRegistry sessionRegistry() {
      SessionRegistry sessionRegistry = new SessionRegistryImpl();
      return sessionRegistry;
    }



    @Bean
    public MyAuthenticationSuccessHandler authenticationSuccessHandler() {
    	MyAuthenticationSuccessHandler myAuthenticationSuccessHandler = new MyAuthenticationSuccessHandler();
        myAuthenticationSuccessHandler.setAlwaysUseDefaultTargetUrl(true);
        myAuthenticationSuccessHandler.setDefaultTargetUrl("/common/user/manage");
        myAuthenticationSuccessHandler.setTargetUrlForSiteAdmin("/common/user/manage");
//        myAuthenticationSuccessHandler.setTargetUrlForNormalUser("/public/product/all");
        List<String> hitIncrementRoleList = new ArrayList<>();
        hitIncrementRoleList.add("ROLE_SITE_ADMIN");
//        hitIncrementRoleList.add("ROLE_CLIENT_ADMIN");
//        hitIncrementRoleList.add("ROLE_WORKSPACE_ADMIN");
//        hitIncrementRoleList.add("ROLE_DELIVERY_ADMIN");
//        hitIncrementRoleList.add("ROLE_USER");
        myAuthenticationSuccessHandler.setHitIncrementRoleList(hitIncrementRoleList);
        return myAuthenticationSuccessHandler;
    }

    @Bean
    public MyAccessDeniedHandler myAccessDeniedHandler() {
      MyAccessDeniedHandler accessDeniedHandler = new MyAccessDeniedHandler();
      accessDeniedHandler.setDefaultAccessDeniedPage("/login?errordenied");
      Map<String, String> roleMap = new HashMap<String, String>();
//      roleMap.put("ROLE_ROOT_ADMIN", "/accessdenied");
      roleMap.put("ROLE_USER", "/accessdenied");
      accessDeniedHandler.setAccessDeniedPageMap(roleMap);
      return accessDeniedHandler;
    }

  }
}
