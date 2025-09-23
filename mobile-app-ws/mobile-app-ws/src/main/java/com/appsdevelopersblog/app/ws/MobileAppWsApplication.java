package com.appsdevelopersblog.app.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MobileAppWsApplication {

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
