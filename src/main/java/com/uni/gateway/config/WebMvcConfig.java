package com.uni.gateway.config;

import com.uni.gateway.zookeeper.ZKCuratorClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author songning
 * @date 2020/4/24
 * description
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean(initMethod = "init")
    public ZKCuratorClient zkCuratorClient() {
        return new ZKCuratorClient();
    }
}
