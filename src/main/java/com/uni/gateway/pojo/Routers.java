package com.uni.gateway.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: songning
 * @date: 2020/4/28 22:01
 */
@Data
public class Routers implements Serializable {

    private static final long serialVersionUID = -5604740934665227785L;

    private String ipPort;

    private String url;

    private Integer weight;

    private Boolean authority;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (ipPort == null ? 0 : ipPort.hashCode());
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        result = prime * result + weight;
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        Routers routers = (Routers) object;
        if (routers.getAuthority() == null || routers.getUrl() == null || routers.getIpPort() == null || routers.getWeight() == null) {
            return false;
        }
        if (authority.equals(routers.getAuthority()) && url.equals(routers.getUrl()) && ipPort.equals(routers.getIpPort()) && weight.equals(routers.getWeight())) {
            return true;
        }
        return false;
    }
}
