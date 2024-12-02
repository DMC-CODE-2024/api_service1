package com.gl.ceir.config.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.gl.ceir.config.repository.oam"},  //
        entityManagerFactoryRef = "oamEntityManagerFactory", transactionManagerRef = "oamTransactionManager", repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)

public class OamDbConfig {

    @Bean(name = "oamEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean oamEntityManagerFactory(@Qualifier("oamDataSource") DataSource dataSource, EntityManagerFactoryBuilder builder) {
        return builder.dataSource(dataSource).packages("com.gl.ceir.config.model.oam")  //
                .persistenceUnit("oam").properties(jpaProperties()).build();
    }

    @Bean(name = "oamDataSource")
    @ConfigurationProperties(prefix = "oam.datasource")               //
    public DataSource oamDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "oamTransactionManager")
    public PlatformTransactionManager oamTransactionManager(@Qualifier("oamEntityManagerFactory") LocalContainerEntityManagerFactoryBean oamEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(oamEntityManagerFactory.getObject()));
    }


    protected Map<String, Object> jpaProperties() {
        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.physical_naming_strategy", org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy.class.getName());
        props.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
        return props;
    }
}




