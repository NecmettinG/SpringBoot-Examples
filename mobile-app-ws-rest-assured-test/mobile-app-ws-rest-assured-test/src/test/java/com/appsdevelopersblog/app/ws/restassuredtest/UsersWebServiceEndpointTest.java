package com.appsdevelopersblog.app.ws.restassuredtest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;

//@FixMethodOrder(MethodSorters.NAME_ASCENDING) is a JUnit4 annotation. We have to use @TestMethodOrder(MethodOrderer.MethodName.class) for JUnit5.
//Our methods will be executed in ascending order according to the initial characters of method names.
@TestMethodOrder(MethodOrderer.MethodName.class)
public class UsersWebServiceEndpointTest {

    private final String CONTEXT_PATH = "/mobile-app-ws";

    private final String EMAIL_ADDRESS = "necmettingedikli611@gmail.com";

    private final String JSON = "application/json";

    @BeforeEach
    void setUp() throws Exception{

        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    /*
    testUserLogin()
    This method is for testing our login feature and checking http headers of Authorization and UserId.
    The reason we named it as "a" is we want to execute our tests in ascending order according to the initial characters of method names.
    This is the last method we are going to execute.*/
    @Test
    final void a(){


        //Teacher used Map<String, String> here.
        Map<String, Object> loginDetails = new HashMap<>();

        loginDetails.put("email", EMAIL_ADDRESS);
        loginDetails.put("password", "794378");

        //We get the response from "/users/login" URL. But this time we will access to http headers!
        Response response = given().
                contentType(JSON).
                accept(JSON).
                body(loginDetails).
                when().
                post(CONTEXT_PATH + "/users/login").
                then().
                statusCode(200).
                extract().
                response();

        //This header contains our jwt token for login.
        String authorizationHeader = response.header("Authorization");
        //This header is custom header we created in AuthenticationFilter and it contains userId that logged in.
        String userId = response.header("UserId");
    }
}
