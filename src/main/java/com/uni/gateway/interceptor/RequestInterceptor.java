package com.uni.gateway.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.uni.gateway.common.Constant;
import com.uni.gateway.pojo.CommonRouters;
import com.uni.gateway.pojo.ErrorResponse;
import com.uni.gateway.tool.GpJoinTools;
import com.uni.gateway.tool.HttpTools;
import com.uni.gateway.tool.LoadBalanceTools;
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
        String result = "", strRoute;
        try {
            log.info("准备判断是否存在此路由: {}", request.getRequestURI());
            try {
                strRoute = HttpTools.httpGet(Constant.ROUTER_URL + "?url=" + request.getRequestURI());
            } catch (Exception e) {
                ErrorResponse errorResponse = ErrorResponse.builder().status(400).message(e.getMessage()).build();
                response.getWriter().append(errorResponse.toString());
                return false;
            }
            if (!StringUtils.isEmpty(strRoute)) {
                log.info("确认存在 {} 路由", request.getRequestURI());
                CommonRouters commonRouters = JSONObject.parseObject(strRoute, CommonRouters.class);
                CommonRouters.Routers routers = LoadBalanceTools.getServer(commonRouters.getDataList());
                if (routers.getAuthority()) {
                    // todo 权限验证
                }
                String redirectIpPort = routers.getIpPort();
                log.info("重定向ipPort: {}", redirectIpPort);
                String redirectUrl = routers.getUrl();
                log.info("重定向url: {}", redirectUrl);
                try {
                    if (Constant.GET.equals(request.getMethod())) {
                        result = HttpTools.httpGet(GpJoinTools.joinGet(redirectIpPort, redirectUrl, request.getParameterMap()));
                    } else if (Constant.POST.equals(request.getMethod())) {
                        result = HttpTools.httpPost(Constant.HTTP + redirectIpPort + redirectUrl, GpJoinTools.joinPost(request));
                    }
                } catch (Exception e) {
                    ErrorResponse errorResponse = ErrorResponse.builder().status(400).message(e.getMessage()).build();
                    response.getWriter().append(errorResponse.toString());
                    return false;
                }
            }
        } catch (Exception e) {
            log.error("preHandle fail: {}", e.getMessage());
            ErrorResponse errorResponse = ErrorResponse.builder().status(400).message(e.getMessage()).build();
            response.getWriter().append(errorResponse.toString());
            return false;
        }
        log.info("返回重定向结果: {}", result);
        response.getWriter().append(result);
        return false;
    }
}
