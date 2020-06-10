package com.vpark.vparkservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.vpark.vparkservice")
public class VparkServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(VparkServiceApplication.class, args);
    }

}
