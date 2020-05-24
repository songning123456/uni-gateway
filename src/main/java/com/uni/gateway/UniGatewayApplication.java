package com.uni.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author sonin
 */
@SpringBootApplication
@EnableDiscoveryClient
public class UniGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(UniGatewayApplication.class, args);
    }

}
