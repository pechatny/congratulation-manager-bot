package com.pechatny.congratulation.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan
@EnableJpaRepositories
@SpringBootApplication
@ConfigurationPropertiesScan
public class CongratulationOrganizerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CongratulationOrganizerApplication.class, args);
    }
}
