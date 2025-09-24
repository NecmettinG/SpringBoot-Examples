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
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        //We put UsernamePasswordAuthenticationToken object into Spring Security Context Holder.
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //We will pass execution to the next filter in chain.
        chain.doFilter(req, res);

    }
    
}
