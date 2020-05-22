package com.uni.gateway.config;

import com.uni.gateway.bean.HostAddrKeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author songning
 * @date 2020/5/22
 * description
 */
@Configuration
public class BeanConfig {

    @Bean
    public HostAddrKeyResolver hostAddrKeyResolver() {
        return new HostAddrKeyResolver();
    }
}
