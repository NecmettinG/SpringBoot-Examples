package com.appsdevelopersblog.app.ws.security;

import com.appsdevelopersblog.app.ws.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//This class is for http web security.
@Configuration
@EnableWebSecurity
public class WebSecurity {

    private final UserService userDetailsService; //Our UserService interface extends UserDetailsService interface.
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurity(UserService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /*This method will accept HttpSecurity object as a parameter.
    Spring will call this method automatically due to @Bean annotation. At the application start up by the way.
    Our code will be executed and HttpSecurity object will be added to Application Context. Spring will use it whenever it needs to use.
    When we send the HTTP request to an API endpoint, spring will take this request through a chain of filters,
    and one of the filters will validate the HTTP request against the security configuration that we will configure in this method.*/
    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {

        //We will use Http security object to get a shared object of AuthenticationManagerBuilder class.
        //We will use this AuthenticationManagerBuilder object to configure which service class in our application will be responsible to load-
        // -user details from database and when user performs logging, spring will use this class to see if our database does have a user with-
        // -provided login credentials.
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        //We will use AuthenticationManagerBuilder's instance to specify which class in our app will be used to look up user details in the-
        // -database. We are required to pass our UserDetailsService object to userDetailsService() method. For spring framework to be able to-
        // -encode the provided user password, and then check if the password that we stored in the database does match it, We need to tell-
        // -spring that which password encoder we used. We used bcrypt password encoder in our app to encode user passwords.
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);

        //We built our authentication manager and we will use this object and our AuthenticationFilter class to create new authentication filter.
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        //We add new authentication filter with creating AuthenticationFilter object, and AuthenticationManager object will be passed-
        //-to super class constructor.
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager);

        //Normally, the default authentication url path in spring boot is "/login" but we created custom login endpoint which is "/users/login".
        authenticationFilter.setFilterProcessesUrl("/users/login");

        http.csrf((csrf) -> csrf.disable()) //We disabled cross-site request forgery which is redundant for our app. Because our api is stateless.
                .authorizeHttpRequests((authz) -> authz.requestMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL)
                        .permitAll().anyRequest().authenticated())//We made post request on /users api endpoint public and we won't get http 403.
                .authenticationManager(authenticationManager).addFilter(authenticationFilter).addFilter(new AuthorizationFilter(authenticationManager))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                //We add new authentication filter with creating AuthenticationFilter object, and AuthenticationManager object will be passed-
                // -to super class constructor. We also updated Http security object with .authenticationManager(authenticationManager) method.
                //We also added another filter which is authorization filter. We also added session management to make the application stateless.
                //This means Spring security will never create Http session and will never use it to obtain security context. This means that-
                //-If there is no Http session created for user authorization, Spring Security will rely on the information that is inside of Jwt.

        // /users api endpoint will be public and open to all users, and all other api endpoints will be protected.
        // Only authenticated users are allowed to invoke them.

        return http.build(); //We are going to build and return http security object from this method.
    }
}
