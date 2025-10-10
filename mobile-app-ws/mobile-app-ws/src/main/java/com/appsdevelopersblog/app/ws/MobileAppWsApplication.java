package com.appsdevelopersblog.app.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MobileAppWsApplication {

    //We can also run our application with using maven in cmd. We have to change working directory to our project that includes pom.xml.
    //Then we type "mvn install". This will build the project, compile the source code, and test it with unit tests.
    //Then if we type "mvn spring-boot:run", We are going to start our web service application in Apache Tomcat Servlet Container.
    //I had to download apache-maven for this.
	public static void main(String[] args) {
		SpringApplication.run(MobileAppWsApplication.class, args);
	}

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){

        return new BCryptPasswordEncoder();
    }

    //With this bean creation way, We have more control over bean creation. We added SpringApplicationContext object to application context.
    //This helps when the class has more than one constructor.
    @Bean
    public SpringApplicationContext springApplicationContext(){

        return new SpringApplicationContext();
    }

}
