package com.appsdevelopersblog.app.ws.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

//This class uses @Component annotation. This means this class's instance is in spring application context and we can inject this instance.
@Component
public class AppProperties {

    //We created an Environment variable to access values from application.properties file. Environment object is available in spring application-
    //-context and we can inject this instance with using @Autowired.
    @Autowired
    private Environment env;

    //This method is for returning the value of the key called "tokenSecret" from application.properties file.
    public String getTokenSecret()
    {
        //We are going to return "tokenSecret" value.
        return env.getProperty("tokenSecret");
    }
}