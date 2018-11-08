package com.bbt.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/8 9:15
 */
@EnableDiscoveryClient
@EnableFeignClients({"com.bbt.cache"})
@ComponentScan({"com.bbt.cache"})
@SpringBootApplication
public class CacheSrvApplication {

    private static Logger logger = LoggerFactory.getLogger(CacheSrvApplication.class);

    public static void main(String[] args) {
        logger.info("cache-srv is starting");
        SpringApplication.run(CacheSrvApplication.class, args);
        logger.info("cache-srv is started");
    }


}
