package com.taskmanager.application;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configuration class for exposing Swagger 2 API documentation.
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	//Note: Swagger API documentation can be accessed at http://localhost:8080/swagger-ui.html while the application is running.
	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.basePackage("com.taskmanager.application.deletableentity.task"))              
          .paths(PathSelectors.any())                          
          .build()
          .apiInfo(apiInfo());                                           
    }
	
	private ApiInfo apiInfo() {
	    return new ApiInfo(
	      "A REST API for managing personal tasks.", 
	      "This API provides a set of endpoints for managing a collection of self-appointed tasks.", 
	      "API TOS", 
	      "Terms of service", 
	      new Contact("Fedor Sosin", "http://localhost:8080", "fsosin0@gmail.com"), 
	      "License of API", "API license URL", Collections.emptyList());
	}
}
