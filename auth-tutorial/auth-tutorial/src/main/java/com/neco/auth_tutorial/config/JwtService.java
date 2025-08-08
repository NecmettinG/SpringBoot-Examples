package com.neco.auth_tutorial.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/*To manipulate Jwt tokens, like generating one, extracting information from the token, we need to add new dependencies to pom.xml.
JWT stands for Json Web Token. JWT consists of three parts which are Header, Payload and Signature.
#Header consists of two parts which are the type of the token(Jwt), and the algorithm for encoding or in other words, sign-in.
#Payload part contains claims. Claims are statements about an entity and typically represent user or additional info.
There are 3 types of claims which are registered, public and private.
#Signature part is used to verify of the center of the JWT. Verifies claims and checks if the message is changed along the way.
You can find an example representation of JWT on JWT-representation.png. */

@Service
public class JwtService{

    //This secret key is for decoding sign in algorithm I guess. It was generated randomly in a key generator website. 256 bit hex.
    private static final String SECRET_KEY = "4f8e3a7c1d2b9f6e7a4c8d0b3e1f5a9c2d7e6f8301b4a5c9e7d2f8b6a1c3e5d";

    public String extractUsername(String jwtToken) {

        return extractClaim(jwtToken, Claims::getSubject);
    }

    public String generateToken(UserDetails userDetails){

        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken( //We are going to generate our token here.
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))//For token generation date.
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // after 24 hours, token will be expired.
                .signWith(getSingInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String jwtToken, UserDetails userDetails){

        final String username = extractUsername(jwtToken);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(jwtToken);
    }

    private boolean isTokenExpired(String jwtToken) {

        return extractExpiration(jwtToken).before(new Date());
    }

    private Date extractExpiration(String jwtToken) {

        return extractClaim(jwtToken, Claims :: getExpiration);
    }

    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver){

        final Claims claims = extractAllClaims(jwtToken);

        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String jwtToken){ //We are going to extract all claims in our token.

        return Jwts //This statement is very confusing.
                .parserBuilder()//For building a parser for our token.
                .setSigningKey(getSingInKey())// For decoding the token. We are required to generate a sing in key from a website.
                .build()//We need to build because it is a builder.
                .parseClaimsJws(jwtToken)//We parsed our token.
                .getBody(); //We get claims from the token.
    }

    private Key getSingInKey(){

        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY); //We are gonna decode our secret key.

        return Keys.hmacShaKeyFor(keyBytes); //This is for using sign-in algorithm.
    }
}
