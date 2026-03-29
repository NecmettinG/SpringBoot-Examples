package com.appsdevelopersblog.app.ws;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//This class is for swagger configuration.
@Configuration
@EnableSwagger2
public class SwaggerConfig {


    @Bean
    public Docket apiDocket(){

        //we provide base package and classes will be scanned by framework and the ones with appropriate annotations will be selected for swagger.
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.appsdevelopersblog.app.ws"))//our base package.
                .paths(PathSelectors.any())//we include all api methods in documentation.
                .build();

        return docket;
    }
}
