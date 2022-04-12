package com.multipledatasource.example.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.multipledatasource.example.company.Company;
import com.zaxxer.hikari.HikariDataSource;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.multipledatasource.example.company.repository",
        entityManagerFactoryRef = "companyEntityManagerFactory",
        transactionManagerRef = "companyTransactionManager")
public class CompanyDataSourceConfiguration {


	@Bean
    @Primary
    @ConfigurationProperties("spring.datasource.company")
    public DataSourceProperties companyDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.company.configuration")
    public DataSource companyDataSource() {
        return companyDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "companyEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean companyEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(companyDataSource())
                .packages(Company.class)
                .build();
    }

    @Primary
    @Bean(name = "companyTransactionManager")
    public PlatformTransactionManager companyTransactionManager(
            final @Qualifier("companyEntityManagerFactory") LocalContainerEntityManagerFactoryBean companyEntityManagerFactory) {
        return new JpaTransactionManager(companyEntityManagerFactory.getObject());
    }

}
