package com.appsdevelopersblog.app.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MobileAppWsApplication extends SpringBootServletInitializer {


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

        return application.sources(MobileAppWsApplication.class);
    }


    //We can also run our application with using maven in cmd. We have to change working directory to our project that includes pom.xml.
    //Then we type "mvn install". This will build the project, compile the source code, and test it with unit tests.
    //Then if we type "mvn spring-boot:run", We are going to start our web service application in Apache Tomcat Servlet Container.
    //I had to download apache-maven for this.
    //We can also copy SNAPSHOT.jar file to desktop or another remote computer and run it with the command in cmd java -jar <filename> right-
    //-after we build our application with "mvn install". That jar fill will appear in target folder.
    //BUT WE ARE GOING TO USE WEB APPLICATION ARCHIVE(WAR) INSTEAD OF JAR. WE CAN DEPLOY THAT WAR INTO EXISTING TOMCAT CONTAINER WHICH -
    //-ALREADY RUNS AND HAS MANY OTHER WEB PROJECTS DEPLOYED TO IT. THIS TOMCAT IS STANDALONE BTW! We also changed pom.xml for war extension.
    //For deleting jar from target folder, change the working directory to our project that includes pom.xml. Then type "mvn clean".
    //Then type "mvn install" again.
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
