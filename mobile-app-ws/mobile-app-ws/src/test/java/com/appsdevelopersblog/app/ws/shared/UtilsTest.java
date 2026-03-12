package com.appsdevelopersblog.app.ws.shared;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

//This test class is for testing the Utils class and its some methods. We will even test JWT tokens if they are expired or not.

//These annotations are needed for integration test. These load up spring context. We can use it to access property files etc.
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UtilsTest {

    //This time, we won't use @Mock. We are going to use typical @Autowired because this test class is accessing Spring Context.
    @Autowired
    Utils utils;

    @BeforeEach
    void setUp() throws Exception{


    }

    //This test is for testing the userId generation in Utils class.
    @Test
    final void testGeneratorUserId(){

        String userId = utils.generateUserId(30);
        String userId2 = utils.generateUserId(30);

        assertNotNull(userId);
        assertNotNull(userId2);

        assertTrue(userId.length() == 30);
        assertTrue(userId2.length() == 30);
        //We are checking that userId and userId2 are not equal!
        assertTrue(!userId.equalsIgnoreCase(userId2));
    }

    @Test
    final void testHasTokenNotExpired(){

        //We are generating a sample token. I we hardcode a token here, Eventually it will be invalid and our test case will fail.
        String token = utils.generateEmailVerificationToken("42rjnvk23432mfjg");
        assertNotNull(token);

        /*Inside of the Utils.hasTokenExpired, there is SecurityConstants.getTokenSecret(). And inside of getTokenSecret, there is:

        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("appProperties");
        return appProperties.getTokenSecret();

        AppProperties also has getTokenSecret and returns "env.getProperty("tokenSecret")" which is the secret key inside of properties file.
        We are able to access spring context in a test class and this makes our test integration test!
         */
        Boolean hasTokenExpired = Utils.hasTokenExpired(token);

        assertFalse(hasTokenExpired);
    }

    //We are going to test expired tokens in this test case.
    @Test
    final void testHasTokenExpired(){

        //This is an expired token. We will test if we can detect expired tokens in our tests.
        String expiredToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJnZWRpa2xpbmVjbzYxQG1haWwuY29tIiwiZXhwIjoxNzYyMTY2NTcyLCJpYXQiOjE3NjEzMDI1NzJ9.8hl4H9LMCbNbURzGtcH5Gn4UYlbhC4tmogVRm8N1XNXX829xHnhdP6YZadevLmmfnBfIVjRcqUnHjcDNSC3rVg";

        Boolean hasTokenExpired = Utils.hasTokenExpired(expiredToken);

        //we will assert hasTokenExpired as true. If our token is not expired, we fill fail in this test.
        assertTrue(hasTokenExpired);
    }
}
