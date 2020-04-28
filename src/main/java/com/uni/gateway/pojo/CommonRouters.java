package com.uni.gateway.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author: songning
 * @date: 2020/4/27 21:12
 */
@Data
public class CommonRouters {

    private Map<String, Object> dataExt;

    private Integer status;

    private List<Routers> data;

    private String message;

    private String timeout;

    private Long total;

    @Data
    public static class Routers implements Serializable {

        private String ipPort;

        private String url;

        private Integer weight;

        private Boolean authority;
    }
}
