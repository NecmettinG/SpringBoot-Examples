package com.example.watercomp;

import org.n52.jackson.datatype.jts.JtsModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.example.watercomp.io.entity")
@EnableJpaRepositories(basePackages = "com.example.watercomp.io.repository")
public class WatercompApplication {

    @Bean
    public JtsModule jtsModule() {
        return new JtsModule();
    }

	public static void main(String[] args) {
		SpringApplication.run(WatercompApplication.class, args);
	}

}
