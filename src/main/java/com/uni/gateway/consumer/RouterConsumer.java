package com.uni.gateway.consumer;

import java.util.List;

public interface RouterConsumer {

    List getRoutersByUrl(String url);

    List getRoutersByTypeAndUrl(String requestType, String url);
}
