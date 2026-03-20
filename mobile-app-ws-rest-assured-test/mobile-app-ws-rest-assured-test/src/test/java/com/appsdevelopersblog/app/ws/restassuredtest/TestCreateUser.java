package com.appsdevelopersblog.app.ws.restassuredtest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class TestCreateUser {


    private final String CONTEXT_PATH = "/mobile-app-ws";

    @BeforeEach
    void setUp() throws Exception {

        //We declared our base URI and port number here.
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    final void testCreateUser() {

        //These are for creating a request body with using hashmaps!
        //You can also examine the structure of the hashmaps! These are important in technical assessments!

        //creating this arraylist with map for storing user addresses.
        List<Map<String, Object>> userAddresses = new ArrayList<>();

        //This is a single user address created with hashmap.
        Map<String, Object> shippingAddress = new HashMap<>();
        shippingAddress.put("city", "Istanbul");
        shippingAddress.put("country", "Turkey");
        shippingAddress.put("streetName", "Aydinlar Street");
        shippingAddress.put("postalCode", "34569");
        shippingAddress.put("type", "SHIPPING");

        Map<String, Object> billingAddress = new HashMap<>();
        billingAddress.put("city", "Istanbul");
        billingAddress.put("country", "Turkey");
        billingAddress.put("streetName", "Aydinlar Street");
        billingAddress.put("postalCode", "34569");
        billingAddress.put("type", "BILLING");

        //We add user address hashmap into our arraylist named userAddresses.
        userAddresses.add(shippingAddress);
        userAddresses.add(billingAddress);

        //This hashmap is for creating request body of a user.
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("firstName", "Necmettin");
        userDetails.put("lastName", "Gedikli");
        userDetails.put("email", "necmettingedikli611@gmail.com");
        userDetails.put("password", "12345678");
        userDetails.put("addresses", userAddresses); //we put our userAddresses array list into our userDetails hashmap.

        //We are creating a Response object. Json response will be parsed and put into response.
        Response response = given(). //after given(), we will declare content type and application headers and request body!.
                contentType("application/json").
                accept("application/json").
                body(userDetails).
                when().//after when(), we will declare our http method with complete address.
                post(CONTEXT_PATH + "/users").
                then().//after then(), we will assert our status code as 200 and content type as json.
                statusCode(200).
                contentType("application/json").
                extract(). // we will extract the entire json response body into Response object with using .extract().response()!
                response();

        //jsonPath returns json representation of the response body!
        String userId = response.jsonPath().getString("userId");

        assertNotNull(userId);
        assertTrue(userId.length() == 30);

        //We will convert Response instance's body into String object.
        String bodyString = response.body().asString();

        try{
            //Then we will convert our stringified response into Json format!
            JSONObject responseBodyJson = new JSONObject(bodyString);
            /*We created a json array for addresses because our addresses are stored in an array list. Our main API also gives-
            addresses as json array.
             */
            JSONArray addresses = responseBodyJson.getJSONArray("addresses");

            assertNotNull(addresses);

            //We are asserting that our addresses Json array has 1 address element currently.
            assertTrue(addresses.length() == 2);

            String addressId = addresses.getJSONObject(0).getString("addressId");
            assertNotNull(addressId);
            //Our utils class generates userId and addressId with length of 30 characters.
            assertTrue(addressId.length() == 30);
        }
        catch (JSONException e){

            fail(e.getMessage());
        }

    }
}
