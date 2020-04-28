package com.uni.gateway.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.uni.gateway.common.Constant;
import com.uni.gateway.pojo.CommonRouters;
import com.uni.gateway.tool.GpJoinTools;
import com.uni.gateway.tool.HttpTools;
import com.uni.gateway.tool.LoadBalanceTools;
import com.uni.gateway.tool.ResponseTools;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.RequestFacade;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
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
        String result = "", strRoute;
        try {
            log.info("准备判断是否存在此路由: {}", request.getRequestURI());
            try {
                strRoute = HttpTools.httpGet(Constant.ROUTER_URL + "?url=" + request.getRequestURI());
            } catch (Exception e) {
                return ResponseTools.error(response, "FIRST_STEP", e.getMessage());
            }
            CommonRouters commonRouters = JSONObject.parseObject(strRoute, CommonRouters.class);
            if (commonRouters.getData() == null) {
                return ResponseTools.error(response, "SECOND_STEP", "routers表里不存在路由: " + request.getRequestURI());
            }
            log.info("确认存在 {} 路由", request.getRequestURI());
            CommonRouters.Routers routers = LoadBalanceTools.getServer(commonRouters.getData());
            if (routers.getAuthority()) {
                // todo 权限验证
            }
            String redirectIpPort = routers.getIpPort();
            log.info("重定向ipPort: {}", redirectIpPort);
            String redirectUrl = routers.getUrl();
            log.info("重定向url: {}", redirectUrl);
            try {
                if (Constant.GET.equals(request.getMethod())) {
                    result = HttpTools.httpGet(GpJoinTools.joinGet(redirectIpPort, redirectUrl, request));
                } else if (Constant.POST.equals(request.getMethod()) && request instanceof RequestFacade) {
                    result = HttpTools.httpPost(Constant.HTTP + redirectIpPort + redirectUrl, GpJoinTools.joinJsonPost(request));
                } else if (Constant.POST.equals(request.getMethod()) && request instanceof StandardMultipartHttpServletRequest) {
                    result = HttpTools.httpPost(Constant.HTTP + redirectIpPort + redirectUrl, GpJoinTools.joinFilePost(request));
                } else {
                    // todo 待补充
                }
            } catch (Exception e) {
                return ResponseTools.error(response, "THIRD_STEP", e.getMessage());
            }
        } catch (Exception e) {
            return ResponseTools.error(response, "FOURTH_STEP", e.getMessage());
        }
        log.info("返回重定向结果: {}", result);
        response.getWriter().append(result);
        return false;
    }
}
