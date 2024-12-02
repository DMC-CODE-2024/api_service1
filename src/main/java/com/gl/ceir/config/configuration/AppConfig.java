/*
package com.gl.ceir.config.configuration;

import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@EnableSwagger2
@Configuration
    
public class AppConfig  implements WebMvcConfigurer {

      @Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.gl.ceir.config"))
				.paths(PathSelectors.regex("/.*")).build().apiInfo(apiEndPointsInfo());
	}

	private ApiInfo apiEndPointsInfo() {
		return new ApiInfoBuilder().title("  Configuration APIs Document")
				.description("Configuration Management REST APIs").license("Apache 2.0")
				.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html").version("1.0.0").build();
	}
    
     @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
       registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/swagger-ui.html**")
                 .addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
     registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/");


    }
       
        @Override
public void addViewControllers(ViewControllerRegistry registry) {
    registry.addRedirectViewController("/CEIR/api-docs", "/CEIR/api-docs");
    registry.addRedirectViewController("/CEIR/swagger-resources/configuration/ui", "/swagger-resources/configuration/ui");
    registry.addRedirectViewController("/CEIR/swagger-resources/configuration/security", "/swagger-resources/configuration/security");
    registry.addRedirectViewController("/CEIR/swagger-resources", "/swagger-resources");
}
  }*/
