package com.line.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;


@SpringBootApplication(exclude = ThymeleafAutoConfiguration.class)
public class BootSecurityJPAExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootSecurityJPAExampleApplication.class, args);
    }
}
