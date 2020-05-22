package com.uni.gateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author songning
 * @date 2020/5/22
 * description
 */
@RestController
public class GatewayController {

    @RequestMapping(value = "/fallback")
    public Map<String, Object> doFallback() {
        Map<String, Object> result = new HashMap<>(2);
        result.put("status", 503);
        result.put("message", "fallback nothing!");
        return result;
    }
}
