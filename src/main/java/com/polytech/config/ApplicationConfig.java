package com.polytech.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 * Created by dev on 3/13/17.
 */
@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "com.polytech.web, com.polytech.repository, com.polytech.business")
@Import(SecurityConfig.class)
@EnableJpaRepositories(basePackages = "com.polytech.repository")
@EntityScan(basePackages = "com.polytech.models")
public class ApplicationConfig {

    @Autowired
    private Environment environment;

    /*@Value("${datasource.driverClassName}")
    private String driverClassName;
    @Value("${datasource.url}")
    private String url;
    @Value("${datasource.userName}")
    private String userName;
    @Value("${datasource.password}")
    private String password;*/


    /*@Bean("dataSource")
    @Profile("PROD")
    public DataSource prodDataSource(){
        String driverClassName = environment.getProperty("datasource.driverClassName");
        String url = environment.getProperty("datasource.url");
        String userName = environment.getProperty("datasource.userName");
        String password = environment.getProperty("datasource.password");
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        dataSource.setUrl(url);
        dataSource.setDriverClassName(driverClassName);
        return dataSource;
    }*/

    @Bean("dataSource")
    @Profile("DEV")
    public DataSource devProdDataSource(){
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).addScript("create-schema.sql").build();
    }
}
