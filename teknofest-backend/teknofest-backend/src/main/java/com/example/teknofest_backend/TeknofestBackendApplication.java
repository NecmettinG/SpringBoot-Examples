package com.example.teknofest_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync //We enabled asynchronous feature for executing python file.
public class TeknofestBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeknofestBackendApplication.class, args);
	}

}
