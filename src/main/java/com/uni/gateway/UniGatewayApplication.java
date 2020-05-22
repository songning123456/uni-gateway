package com.uni.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author sonin
 */
@SpringBootApplication
@EnableEurekaClient
public class UniGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(UniGatewayApplication.class, args);
    }

}
