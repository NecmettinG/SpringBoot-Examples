package com.appsdevelopersblog.app.ws.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

//This filter class is for validating provided JWT access token. This is needed when a client application sends a request to a protected API endpoint.
//For example, when client application sends a request to update user details, The API endpoint that is for updating user details should be-
//-protected and only authorized users can invoke this endpoint. A request to a protected API endpoint must be protected with JWT access token.
//If JWT token is correct, its owner is authorized to perform the update operation. Request will be accepted.

//BasicAuthenticationFilter class is from Spring Security and it processes HTTP requests with basic authorization headers,
// And puts the result into Spring Security Context.
/*So basically we are going to read JWT access token from authorization header, validate JWT and if it is correct, we will put user authentication-
* -information into Spring Security Context Holder.*/
public class AuthorizationFilter extends BasicAuthenticationFilter {

    public AuthorizationFilter(AuthenticationManager authManager) {

        super(authManager);
    }

    //This method comes from BasicAuthenticationFilter, and it reads authorization header from HTTP requests.
    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {

        //This line reads the value of Authorization header from HTTP request. Which is JWT access token.
        String header = req.getHeader(SecurityConstants.HEADER_STRING);//header name is "Authorization".

        //If Authorization header is null or doesn't start with "Bearer ":
        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {

            chain.doFilter(req, res);//We will pass the execution to the next filter in chain.
            return;
        }

        //If JWT access token exists in authorization header, we will parse it and extract user authentication details:

        //if JWT is valid, getAuthentication will return UsernamePasswordAuthenticationToken object.
        //UsernamePasswordAuthenticationToken is a simple class for representing username and password. We only used username aka email.
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        //We put UsernamePasswordAuthenticationToken object into Spring Security Context Holder.
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //We will pass execution to the next filter in chain.
        chain.doFilter(req, res);

    }

    //We will validate and parse JWT in this method.
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request){

        //We read authorization header which includes jwt.
        String authorizationHeader = request.getHeader(SecurityConstants.HEADER_STRING);

        //If authorization header is empty, we return null and request authorization won't be successful.
        if (authorizationHeader == null) {
            return null;
        }

        //We take the value of authorization header and get rid of "Bearer " prefix. The authorization header only contains jwt now.
        String token = authorizationHeader.replace(SecurityConstants.TOKEN_PREFIX, "");

        //To validate jwt access token, We'll need to use the token secret value from properties file.
        //We will prepare secret key using the token secret value from application.properties file.
        //Look to SecurityConstants and AppProperties classes to learn more about token secret value.
        byte[] secretKeyBytes = SecurityConstants.getTokenSecret().getBytes();
        SecretKey key = Keys.hmacShaKeyFor(secretKeyBytes);

        //We use the created secret key to prepare the JwtParser object.
        JwtParser parser = Jwts.parser().verifyWith(key).build();

        //We will use JwtParser object to parse Jwt. If jwt is signed with a correct secret key, Parse method will be able to parse it and-
        //-return object which will contain a list of token claims. Token claims are information that are encoded in json web token. If we-
        //-decode jwt, we will see that it mainly consists of key-value pairs.
        Claims claims = parser.parseSignedClaims(token).getPayload();

        //"sub" means subject and subject is one of the claims. We read the value of the subject key and assign it to subject variable.
        //Subject claim is usually used to store username or userId. It is a value that is used to identify principal user or a user who-
        //-has successfully logged in or for whom this jwt was issued.
        String subject = (String) claims.get("sub");

        //If subject is null, we return null from this function. Authorization won't be successful.
        if(subject == null){
            return null;
        }

        //We will return a new object of UsernamePasswordAuthenticationToken. This object is used to hold principle user credentials.
        //We will add this object to Spring Security Context Holder once we return this object. This will mean a successful authorization.-
        //The validation of jwt is successful.
        //At this moment, we can create this object with subject value aka username only. It is enough for now. No password.
        return new UsernamePasswordAuthenticationToken(subject, null, new ArrayList<>());
    }
}
