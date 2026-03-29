package com.appsdevelopersblog.app.ws;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.parameters.RequestBody;
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
        OpenAPI openAPI = new OpenAPI()
                .info(new Info().title("Your API Title")
                        .description("Your API Description")
                        .version("1.0"));
        /*instead of creating a fake controller and fake login method like implemented in teacher's tutorial, we added our real login endpoint-
        * -to the OpenAPI model.
        * Swagger only auto-discovers controller endpoints. Since login is handled by AuthenticationFilter (not a controller method),
        * it must be added manually to the OpenAPI model.
         */
        Schema<?> loginRequestSchema = new ObjectSchema()
                .addProperty("email", new StringSchema())
                .addProperty("password", new StringSchema().format("password"));

        RequestBody loginRequestBody = new RequestBody()
                .required(true)
                .content(new Content().addMediaType("application/json",
                        new MediaType().schema(loginRequestSchema)));

        ApiResponse loginSuccessResponse = new ApiResponse()
                .description("Authentication successful. JWT is returned in response headers.")
                .addHeaderObject("Authorization",
                        new Header().description("Bearer JWT token").schema(new StringSchema()))
                .addHeaderObject("UserId",
                        new Header().description("Authenticated user id").schema(new StringSchema()));

        ApiResponse loginFailureResponse = new ApiResponse()
                .description("Authentication failed. Invalid email or password.");

        Operation loginOperation = new Operation()
                .addTagsItem("users")
                .summary("Authenticate user")
                .description("Handled by Spring Security filter. Use this endpoint to obtain JWT.")
                .requestBody(loginRequestBody)
                .responses(new ApiResponses()
                        .addApiResponse("200", loginSuccessResponse)
                        .addApiResponse("401", loginFailureResponse));

        openAPI.path("/users/login", new PathItem().post(loginOperation));

        return openAPI;
    }
}
