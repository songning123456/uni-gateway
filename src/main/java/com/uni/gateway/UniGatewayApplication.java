package com.uni.gateway;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class UniGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(UniGatewayApplication.class, args);
    }

}
