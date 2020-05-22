package com.uni.gateway.bean;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author songning
 * @date 2020/5/22
 * description
 */
public class HostAddrKeyResolver implements KeyResolver {

    @Override
    public Mono<String> resolve(ServerWebExchange serverWebExchange) {
        return Mono.just(serverWebExchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
    }

    public HostAddrKeyResolver hostAddrKeyResolver() {
        return new HostAddrKeyResolver();
    }
}
