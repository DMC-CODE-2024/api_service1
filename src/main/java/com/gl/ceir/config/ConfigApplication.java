package com.gl.ceir.config;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication
@EnableAutoConfiguration
@EnableCaching
@EnableFeignClients
@EnableJpaAuditing
@EnableWebMvc
@EntityScan({"com.gl.ceir.config.model"})
@ComponentScan({"com.gl.ceir.config"})
@ImportAutoConfiguration({FeignAutoConfiguration.class})
@EnableEncryptableProperties

// @EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)

public class ConfigApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ConfigApplication.class, args);
    }


}
