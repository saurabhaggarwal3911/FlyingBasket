package com.config;


import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.utility.ApplicationConstants;
import com.utility.RoutingDataSource;

@Profile("!test")
// 1
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.repository", entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager")
public class ConfigurationForDatabase {

  @Bean(name = "dataSource1")
  @ConfigurationProperties(prefix = "spring.datasource.grocery")
  public DataSource dataSource1() {
    return DataSourceBuilder.create().build();
  }


  @Bean(name = "dataSource")
  @Primary
  public RoutingDataSource dataSource() {
    RoutingDataSource resolver = new RoutingDataSource();

    Map<Object, Object> dataSources = new HashMap<Object, Object>();
    dataSources.put(ApplicationConstants.GROCERY, dataSource1());
    resolver.setTargetDataSources(dataSources);

    return resolver;
  }

  @Primary
  @PersistenceContext(unitName = "primary")
  @Bean(name = "entityManagerFactory")
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
      @Qualifier("dataSource") DataSource dataSource) {
    return builder.dataSource(dataSource).persistenceUnit("primary").packages("com.entity").build();
  }

  // @Bean(name = "transactionManager")
  // public PlatformTransactionManager transactionManager(
  // @Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
  // return new JpaTransactionManager(entityManagerFactory);
  // }


  @Bean(name = "transactionManager")
  public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory,
      @Qualifier("dataSource") DataSource dataSource) {
    JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
    jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
    jpaTransactionManager.setDataSource(dataSource);
    return jpaTransactionManager;
  }
}
