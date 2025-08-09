package com.neco.auth_tutorial.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/* Whenever We receive http request, it will be filtered with OncePerRequestFilter.
This class will automatically work whenever it receives a http request. We also need to pass a Jwt authentication token
to this filter. You can check auth-schema for better observation.*/

/*We also need to extract email from the http request to check if we have that email that is already existed in our database.
To do that, We have to create a JwtService class to extract the email.*/

@Component //@Component is for creating a spring bean. @Service and @Repository extend from @Component.
@RequiredArgsConstructor //Lombok will create a constructor for necessary final attributes like private final String etc.
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,//http request.
                                    @NonNull HttpServletResponse response,//http response.
                                    @NonNull FilterChain filterChain //a list of different filters.
                                    ) throws ServletException, IOException {
        /*@NonNull annotation is for declaring a parameter that is nonnull. We can't pass null value to these parameters.*/

        final String authHeader = request.getHeader("Authorization"); //This String will take our token value
        // from authentication header. We will get the token value from Authorization header.
        final String jwtToken; //Token message always starts with "Bearer " so we are going to adjust it and pass it
        // to this variable. We will get rid of "Bearer "

        final String userEmail;//We are also gonna extract user email from the token so we created this variable.

        if(authHeader == null || !authHeader.startsWith("Bearer ")){//If the token is null or doesn't start with "Bearer ".
            filterChain.doFilter(request, response);//We are gonna pass request and response to next filter.
            return; //stop the function.
        }

        jwtToken = authHeader.substring(7);//we got rid of "Bearer ".Now, our token string will start at index 8.

        userEmail = jwtService.extractUsername(jwtToken);

        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if(jwtService.isTokenValid(jwtToken, userDetails)){

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
