package com.bbt.user;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@EnableEurekaClient
@MapperScan({"com.bbt.user.mapper"})
@ComponentScan({"com.bbt"})
@SpringBootApplication
public class UserSrvApplication {

    private static Logger logger = LoggerFactory.getLogger(UserSrvApplication.class);

    public static void main(String[] args) {
        logger.info("user-srv is starting");
        SpringApplication.run(UserSrvApplication.class, args);
        logger.info("user-srv is started");
    }
}
