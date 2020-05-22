package com.uni.gateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author songning
 * @date 2020/5/22
 * description
 */
@RestController
public class GatewayController {

    @RequestMapping(value = "/fallback")
    public String fallback() {
        return "fallback nothing!";
    }
}
