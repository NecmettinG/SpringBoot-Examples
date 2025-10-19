package com.appsdevelopersblog.app.ws.shared;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class Utils{

    private final Random RANDOM = new SecureRandom();
    private final String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public String generateUserId(int length){

        return generateRandomString(length);
    }

    public String generateAddressId(int length){

        return generateRandomString(length);
    }

    private String generateRandomString(int length){

        StringBuilder returnValue = new StringBuilder(length); //We are going to build a random string for UserId.
        //StringBuilder allows us to use some methods for strings like .append() or .insert().
        for(int x = 0; x < length; x++){

            returnValue.append(alphabet.charAt(RANDOM.nextInt(alphabet.length())));
        }

        return new String(returnValue);
    }
}
