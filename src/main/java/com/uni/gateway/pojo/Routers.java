package com.uni.gateway.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: songning
 * @date: 2020/4/28 22:01
 */
@Data
public class Routers implements Serializable {

    private String ipPort;

    private String url;

    private Integer weight;

    private Boolean authority;
}
