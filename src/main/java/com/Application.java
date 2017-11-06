package com;


import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

import com.utility.GeneralUtil;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
// @EnableTransactionManagement
@EnableCaching
//@EnableScheduling
// @EnableJpaRepositories(basePackages="com.repository",
// entityManagerFactoryRef="entityManagerFactoryForCommon")
// @EntityScan(basePackages="com.entity")
public class Application {
    
  public static void main(String[] args) {
    SpringApplication springApplication = new SpringApplication(Application.class);
    springApplication.run(args);
    springApplication.setBannerMode(Mode.OFF);

   GeneralUtil.printMemory();
    
  }

}
