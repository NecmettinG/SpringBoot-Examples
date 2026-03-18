package com.appsdevelopersblog.app.ws.restassuredtest;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UsersWebServiceEndpointTest {

    private final String CONTEXT_PATH = "/mobile-app-ws";


    @BeforeEach
    void setUp() throws Exception{

        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    final void testUserLogin(){


    }
}
