package com.bbt.order;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@EnableEurekaClient
@MapperScan({"com.bbt.order.mapper","com.bbt.mq.mapper"})
@ComponentScan({"com.bbt"})
@SpringBootApplication
public class OrderSrvApplication {

    private static Logger logger = LoggerFactory.getLogger(OrderSrvApplication.class);

    public static void main(String[] args) {
        logger.info("order-srv is starting");
        SpringApplication.run(OrderSrvApplication.class, args);
        logger.info("order-srv is started");
    }
}
