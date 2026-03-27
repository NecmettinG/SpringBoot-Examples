package com.appsdevelopersblog.app.ws;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//This class is a configuration class for enabling CORS.
//We declared CorsConfigurationSource bean in WebSecurity class and security uses it first for specifying CORS rules.
//If CorsConfigurationSource bean is not present, WebConfig class which implements WebMvcConfigurer specifies CORS rules.
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){

        //This is how you add CORS for email-verification endpoint.
        //registry.addMapping("/users/email-verification");

        /*We added CORS to all rest controller endpoints and all rest controller methods. We can also allow several HTTP methods for CORS-
        with .allowedMethods("GET", "POST", "PUT"). We haven't added delete in this case. Put asterisk if you want to allow all methods.*/
        registry
                .addMapping("/**")
                .allowedMethods("*") //All HTTP methods are allowed.
                .allowedOrigins("*"); //We allowed all origins for CORS.
    }
}
