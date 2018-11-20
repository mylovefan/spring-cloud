package com.bbt.operate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/20 10:02
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.bbt"})
@Configuration
@ImportResource(locations={"classpath:spring.xml"})
@ComponentScan(basePackages={"com.bbt"})
public class OperatorApiApplication {

    private static Logger logger = LoggerFactory.getLogger(OperatorApiApplication.class);

    public static void main(String[] args) {
        logger.info("operator-api is starting");
        SpringApplication.run(OperatorApiApplication.class, args);
        logger.info("operator-api is started");
    }
}
