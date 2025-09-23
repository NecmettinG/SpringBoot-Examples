package com.appsdevelopersblog.app.ws.security;

import com.appsdevelopersblog.app.ws.SpringApplicationContext;
import com.appsdevelopersblog.app.ws.service.UserService;
import com.appsdevelopersblog.app.ws.shared.dto.UserDto;
import com.appsdevelopersblog.app.ws.ui.model.request.UserLoginRequestModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
//THIS CLASS IS NOT A BEAN. WE CANNOT INJECT OBJECTS WITH USING @Autowired ANNOTATION.
//WE ARE REQUIRED TO USE UserServiceImpl CLASS TO ACCESS userId TO RETURN IT IN http response header.
//THERE IS A WAY THAT WE CAN INJECT OBJECTS INSIDE A CLASS THAT IS NOT A SPRING BEAN. We are going to use UserServiceImpl in successfulAuthentication.

/*This class will be used to authenticate user when they send request to perform user login.
* When application receives a HTTP request to perform user login, this filter class will be triggered.
* It will read username and password from HTTP request and pass them to spring framework.
* Spring will validate these provided user credentials and if they are correct, we will be able to generate access token.*/
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//UsernamePasswordAuthenticationFilter is a spring security class for processing authentication information when it is submitted in HTTP request.

    //AuthenticationManager is used during authentication process and it has only one method called "authenticate".
    //To inject UserServiceImpl object, we can also use constructor based dependency injection but we won't use it, there is another way.
    //And that way is accessing UserServiceImpl object, which is a bean, from application context. But we have to create a helper class.
    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    //attemptAuthentication method is a part of UsernamePasswordAuthenticationFilter class, and we are overriding it.
    //It receives two parameters which are Http request and Http response objects.
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {

            //We are going to read our json payload from Http request and these information will be mapped into UserLoginRequestModel object.
            //UserLoginRequestModel class is created by us, and it has attributes of email and password.
            UserLoginRequestModel creds = new ObjectMapper().readValue(req.getInputStream(), UserLoginRequestModel.class);

            //We created UsernamePasswordAuthenticationToken object with using UserLoginRequestModel instance.
            //UsernamePasswordAuthenticationToken is a simple class for representing username and password.
            //We pass this simple class's object as a parameter to authenticate method to trigger authentication process.
            //After invoking authenticate method, spring will invoke loadUserByUsername method from UserServiceImpl class.

//If provided username and password are verified and correct,
// the authentication will be successful and successfulAuthentication method will be invoked by spring.
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Once authentication is successful, this method will be invoked by spring.
    //In this method, we are going to generate jwt access token and return this token in http response header.
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        //To generate JSON web token, We used the value of Token Secret from SecurityConstants class and got its bytes.
        //Then we used Base64 encoder to encode these bytes into a new byte array called secretKeyBytes.
        byte[] secretKeyBytes = Base64.getEncoder().encode(SecurityConstants.TOKEN_SECRET.getBytes());

        //We used the byte array to generate secret key which will be used to sign jwt access token in the code below.
        SecretKey secretKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());

        //For setting generation date and expiration date for jwt token, We use Instant object.
        Instant now = Instant.now();

        //We used auth object for getting authenticated username.
        String userName = ((User) auth.getPrincipal()).getUsername();

        //To generate jwt token, we use Jwts.builder().
        String token = Jwts.builder()
                .setSubject(userName) //We used authenticated username as a subject to jwt token.
                .setExpiration( //Setting token expiration time with using Expiration Time from SecurityConstants class.
                        Date.from(now.plusMillis(SecurityConstants.EXPIRATION_TIME)))
                .setIssuedAt(Date.from(now)).signWith(secretKey, SignatureAlgorithm.HS512).compact();
        //setting generation time and signing jwt access token with a secret key. compact method will return a final value of jwt access token.

        //We get UserServiceImpl object from application context and this class has already implemented UserService interface,
        // so we use it as datatype.
        // We also typed "userServiceImpl" inside getBean method because object name of a class starts with lowercase in application context.
        //The reason we use (UserService) expression is getBean method returns "Object". We convert Object into UserService with this.
        //It is called TYPE CAST!!! The compiler knows what methods we can call in this way. Without cast, compiler only knows we have an Object-
        //-and won't let us call UserServiceImpl methods.
        UserService userService = (UserService)SpringApplicationContext.getBean("userServiceImpl");

        //We got a particular user from database with particular email. userName represents email!
        UserDto userDto = userService.getUser(userName);

        //access token is generated and added as a header to a http response object.
        //When the client application receives http response, it will be able to read this jwt access token from http response header.
        res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);

        //We also add UserId inside http response header.
        res.addHeader("UserId", userDto.getUserId());
    }
}
