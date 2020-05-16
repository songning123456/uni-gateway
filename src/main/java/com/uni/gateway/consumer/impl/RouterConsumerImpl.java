package com.uni.gateway.consumer.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.uni.dubbo.service.RouterService;
import com.uni.gateway.consumer.RouterConsumer;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dell
 */
@Service
public class RouterConsumerImpl implements RouterConsumer {

    @Reference(check = false)
    private RouterService routerService;

    @Override
    public List getRoutersByUrl(String url) {
        return JSON.parseArray(routerService.getRoutersByUrl(url));
    }
}
