package com.rpgroyale.gamedataservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class GamedataserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GamedataserviceApplication.class, args);
    }
}
