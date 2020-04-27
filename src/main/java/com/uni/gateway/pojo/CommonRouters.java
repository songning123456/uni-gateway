package com.uni.gateway.pojo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author: songning
 * @date: 2020/4/27 21:12
 */
@Data
public class CommonRouters {

    private Map<String, Object> dataMap;

    private Integer status;

    private List<Routers> dataList;

    private Routers data;

    private Integer errCode;

    private String message;

    private String timeout;

    private Long total;

    @Data
    public static class Routers {

        private String ipPort;

        private String url;

        private Integer weight;

        private Boolean authority;
    }
}
