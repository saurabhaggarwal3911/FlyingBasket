package com.config;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Profile("!test")
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.commondb.repository", entityManagerFactoryRef = "entityManagerFactoryForCommonDb", transactionManagerRef = "transactionManagerForCommonDb")
public class ConfigurationForCommonDatabase {

    @Bean(name = "dataSourceForCommonDb")
    @ConfigurationProperties(prefix = "spring.datasource.common")
    public DataSource dataSource() {
	return DataSourceBuilder.create().build();
    }

    @PersistenceContext(unitName = "secondary")
    @Bean(name = "entityManagerFactoryForCommonDb")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("dataSourceForCommonDb") DataSource dataSource) {
	return builder.dataSource(dataSource).persistenceUnit("secondary").packages("com.commondb.entity").build();
    }

    @Bean(name = "transactionManagerForCommonDb")
    public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactoryForCommonDb") EntityManagerFactory entityManagerFactory, @Qualifier("dataSourceForCommonDb") DataSource dataSource) {
	JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
	jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
	jpaTransactionManager.setDataSource(dataSource);
	return jpaTransactionManager;
    }
}
