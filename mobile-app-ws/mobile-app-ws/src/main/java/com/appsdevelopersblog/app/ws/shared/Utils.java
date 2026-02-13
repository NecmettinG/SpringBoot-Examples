package com.appsdevelopersblog.app.ws.shared;

import com.appsdevelopersblog.app.ws.security.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Date;
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

    public static boolean hasTokenExpired(String token){

        //false by default.
        boolean returnValue = false;

        try {

            //We used our secret key in application.properties. We are going to use it for decoding our jwt.
            byte[] secretKeyBytes = SecurityConstants.getTokenSecret().getBytes();
            SecretKey key = Keys.hmacShaKeyFor(secretKeyBytes);

            //We are going to decode our jwt.
            JwtParser parser = Jwts.parser().verifyWith(key).build();

            //We get claims inside of our token. We are able to access some information like token expiration date etc.
            Claims claims = parser.parseSignedClaims(token).getPayload();

            //We get token expiration date from our claims.
            Date tokenExpirationDate = claims.getExpiration();
            Date todayDate = new Date();

            //it compares if jwt's expiration date and today's date. Returns true if expiration date comes before today's date.
            returnValue = tokenExpirationDate.before(todayDate);
        }
        catch (ExpiredJwtException ex) {
            //This is for ensuring if the code below has something wrong.
            returnValue = true;
        }

        return returnValue;
    }

    public String generateEmailVerificationToken(String userId){

        return generateToken(userId);
    }

    private String generateToken(String userId) {
        //We are going to generate jwt token for Email.
        byte[] secretKeyBytes = SecurityConstants.getTokenSecret().getBytes();
        SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyBytes);
        Instant now = Instant.now();

        return Jwts.builder()
                .subject(userId)
                .expiration(Date.from(now.plusMillis(SecurityConstants.EXPIRATION_TIME)))
                .issuedAt(Date.from(now))
                .signWith(secretKey)
                .compact();
    }
}
