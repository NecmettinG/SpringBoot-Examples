package com.appsdevelopersblog.app.ws.security;

import com.appsdevelopersblog.app.ws.SpringApplicationContext;

//This class is not annotated with any spring annotations and it is not a spring bean. For reading token secret value from properties file,-
//-We can convert this class into spring bean or access properties file directly via spring application context. We are going to use spring-
//-application context for token secret value. We will access application environment values directly from spring application context.
//To do that, We also created a class called AppProperties.
public class SecurityConstants {

    public static final long EXPIRATION_TIME = 864000000; //miliseconds that worth 10 days.
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users";
    public static final String VERIFICATION_EMAIL_URL = "/users/email-verification";
    //We will define our Token Secret value inside of application.properties file for security reasons. We are going to read that value from there.
    //This is because Token Secret value is updated time to time for security. And it is better to define it in properties file.
    //public static final String TOKEN_SECRET="jf9i4jgu83nfl0jf9i4jgu83nfl0jf9i4jgu83nfl0jf9i4jgu83nfl0jf9i4jgu83nfl0jf9i4jgu83nfl0";

    //This method is for getting "tokenSecret" key's value from AppProperties instance and this instance also gets that value from properties file.
    public static String getTokenSecret()
    {
        //We get an AppProperties instance from spring application context to inject this bean to a variable that belongs to non-bean class.
        //AppProperties is a @Component bean by the way. (You should use lowercase letter when you want to get an instance from app. context btw.)
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("appProperties");
        //we are going to return token secret value. And we will use this value in AuthorizationFilter and AuthenticationFilter classes.
        return appProperties.getTokenSecret();
    }

}
