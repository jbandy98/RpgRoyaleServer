package com.rpgroyale.worldservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class WorldserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorldserviceApplication.class, args);
    }
}
