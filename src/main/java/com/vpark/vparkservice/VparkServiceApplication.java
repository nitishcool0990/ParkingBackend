package com.vpark.vparkservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:messages.properties")
public class VparkServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(VparkServiceApplication.class, args);
    }

}
