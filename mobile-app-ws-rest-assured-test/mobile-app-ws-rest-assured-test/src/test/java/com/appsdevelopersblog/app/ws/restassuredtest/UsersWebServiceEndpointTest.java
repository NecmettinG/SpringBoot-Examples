package com.appsdevelopersblog.app.ws.restassuredtest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

//@FixMethodOrder(MethodSorters.NAME_ASCENDING) is a JUnit4 annotation. We have to use @TestMethodOrder(MethodOrderer.MethodName.class) for JUnit5.
//Our methods will be executed in ascending order according to the initial characters of method names.
@TestMethodOrder(MethodOrderer.MethodName.class)
public class UsersWebServiceEndpointTest {

    private final String CONTEXT_PATH = "/mobile-app-ws";

    private final String EMAIL_ADDRESS = "necmettingedikli611@gmail.com";

    private final String JSON = "application/json";

    private static String userId;
    private static String authorizationHeader;

    @BeforeEach
    void setUp() throws Exception {

        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    /*
    testUserLogin()
    This method is for testing our login feature and checking http headers of Authorization and UserId.
    The reason we named it as "a" is we want to execute our tests in ascending order according to the initial characters of method names.
    This is the first method we are going to execute.*/
    @Test
    final void a() {


        Map<String, String> loginDetails = new HashMap<>();

        loginDetails.put("email", EMAIL_ADDRESS);
        loginDetails.put("password", "12345678");

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
        authorizationHeader = response.header("Authorization");
        //This header is custom header we created in AuthenticationFilter and it contains userId that logged in.
        userId = response.header("UserId");

        assertNotNull(authorizationHeader);
        assertNotNull(userId);
    }

    /*
    testGetUserDetails()
    */
    @Test
    final void b() {

        Response response = given().
                header("Authorization", authorizationHeader). //We passed our jwt authorization token to "Authorization" header.
                pathParam("id", userId). //We can also define path parameters in this way.
                accept(JSON).
                when().
                get(CONTEXT_PATH + "/users/{id}").//{id} is path parameter.
                then().
                statusCode(200).
                contentType(JSON).
                extract().
                response();

        String userPublicId = response.jsonPath().getString("userId");
        String userEmail = response.jsonPath().getString("email");
        String firstName = response.jsonPath().getString("firstName");
        String lastName = response.jsonPath().getString("lastName");

        //getList turns json list into array list! it holds list of maps.
        List<Map<String, String>> addresses = response.jsonPath().getList("addresses");

        String addressId = addresses.get(0).get("addressId"); //We fetched index 0's userId value.

        assertNotNull(userPublicId);
        assertNotNull(userEmail);
        assertNotNull(firstName);
        assertNotNull(lastName);

        assertEquals(EMAIL_ADDRESS, userEmail);

        assertTrue(addresses.size() == 2);
        assertTrue(addressId.length() == 30);
    }
}
