package com.rpgroyale.combatserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class CombatserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(CombatserverApplication.class, args);
    }

}
