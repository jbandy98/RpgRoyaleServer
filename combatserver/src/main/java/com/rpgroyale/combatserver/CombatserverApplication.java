package com.rpgroyale.combatserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@Slf4j
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class CombatserverApplication {

    public static void main(String[] args) {
        log.info("web application context: " + WebApplicationContext.CLASSPATH_URL_PREFIX);
        SpringApplication.run(CombatserverApplication.class, args);
    }

}
