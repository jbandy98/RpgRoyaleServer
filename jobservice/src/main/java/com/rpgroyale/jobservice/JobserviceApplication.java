package com.rpgroyale.jobservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class JobserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobserviceApplication.class, args);
    }
}
