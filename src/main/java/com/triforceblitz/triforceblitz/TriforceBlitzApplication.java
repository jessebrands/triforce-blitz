package com.triforceblitz.triforceblitz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TriforceBlitzApplication {
    public static void main(String[] args) {
        SpringApplication.run(TriforceBlitzApplication.class, args);
    }
}
