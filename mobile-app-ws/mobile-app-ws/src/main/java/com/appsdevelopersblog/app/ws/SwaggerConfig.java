package com.appsdevelopersblog.app.ws;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//This class is for swagger configuration.
@Configuration
//@EnableSwagger2
//This annotation enables to pass jwt tokens on swagger ui. There will be a button called "authenticate" on the top right corner, and we will-
//-paste our jwt token here. No need to pass jwt token on each method section in swagger ui.
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {


    //This implementation is from teacher's updated repository. The old implementation from videos (Docket bean, LinkDiscoverers bean, swagger2-
    // -and swagger-ui dependencies has been discarded. We will use OpenAPI bean.)
    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Your API Title")
                        .description("Your API Description")
                        .version("1.0"));
    }
}
