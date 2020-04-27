package com.uni.gateway.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.uni.gateway.request.RequestWrapper;
import com.uni.gateway.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author songning
 * @date 2020/4/27
 * description
 */
@Slf4j
@Component
public class RequestInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uuid = StringUtils.getRandomUuid("");
        String params = (null != request.getParameterMap() && request.getParameterMap().size() > 0) ? JSONObject.toJSONString(request.getParameterMap()) : new RequestWrapper(request).getBody();
        log.info(String.format("Request[%s] IP:[%s] URL:[%s], Protocol:[%s], Params:%s", uuid, getIpAddr(request), request.getRequestURL(), request.getProtocol(), params));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", 123);
        jsonObject.put("status", 200);
        response.getWriter().append(jsonObject.toString());
        return false;
    }


    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }
        if (ip.split(",").length > 1) {
            ip = ip.split(",")[0];
        }
        return ip;
    }
}
