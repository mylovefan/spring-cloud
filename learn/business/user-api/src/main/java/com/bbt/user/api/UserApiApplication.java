package com.bbt.user.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.bbt"})
@EnableEurekaClient
@Configuration
@ComponentScan(basePackages = {"com.bbt"})
public class UserApiApplication {

    private static Logger logger = LoggerFactory.getLogger(UserApiApplication.class);

    public static void main(String[] args) {
        logger.info("user-api is starting");
        SpringApplication.run(UserApiApplication.class, args);
        logger.info("user-api is started");
    }
}

