package com.uni.gateway.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.uni.gateway.common.Constant;
import com.uni.gateway.tool.GpJoinTools;
import com.uni.gateway.tool.HttpTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        String result = "";
        try {
            log.info("准备判断是否存在此路由: {}", request.getRequestURI());
            String strRoute = HttpTools.httpGet(Constant.ROUTER_URL + "?url=" + request.getRequestURI());
            if (!StringUtils.isEmpty(strRoute)) {
                log.info("确认存在 {} 路由", request.getRequestURI());
                JSONObject routeMap = JSONObject.parseObject(strRoute);
                if (Boolean.parseBoolean(String.valueOf(((JSONObject) routeMap.get("data")).get("authority")))) {
                    // todo 权限验证
                }
                String redirectIps = String.valueOf(((JSONObject) routeMap.get("data")).get("ipPorts"));
                log.info("重定向ipPorts群: {}", redirectIps);
                String redirectUrl = String.valueOf(((JSONObject) routeMap.get("data")).get("url"));
                log.info("重定向url: {}", redirectUrl);
                if (Constant.GET.equals(request.getMethod())) {
                    result = HttpTools.httpGet(GpJoinTools.joinGet(redirectIps, redirectUrl, request.getParameterMap()));
                } else if (Constant.POST.equals(request.getMethod())) {
                    result = HttpTools.httpPost(Constant.HTTP + redirectIps + redirectUrl, GpJoinTools.joinPost(request));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("preHandle fail: {}", e.getMessage());
        }
        log.info("返回重定向结果: {}", result);
        response.getWriter().append(result);
        return false;
    }
}
