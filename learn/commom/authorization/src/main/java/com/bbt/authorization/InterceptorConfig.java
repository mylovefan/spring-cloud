package com.bbt.authorization;

import com.bbt.authorization.interceptor.AuthorizationInterceptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/8 14:55
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    @ConfigurationProperties(prefix = "com.bbt.authorization.interceptor")
    @Bean
    public AuthorizationInterceptor authorizationInterceptor() {
        return new AuthorizationInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
