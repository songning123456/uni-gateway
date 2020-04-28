package com.uni.gateway.interceptor;

import com.uni.gateway.common.Constant;
import com.uni.gateway.pojo.UniResponse;
import com.uni.gateway.pojo.Routers;
import com.uni.gateway.tool.GpJoinTools;
import com.uni.gateway.tool.HttpTools;
import com.uni.gateway.tool.LoadBalanceTools;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.RequestFacade;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

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
            List<Routers> routersList = Constant.routersCache.stream().filter(item -> item.getUrl().equals(request.getRequestURI())).collect(Collectors.toList());
            if (routersList.isEmpty()) {
                return error(response, "routers表里不存在此路由");
            }
            log.info("确认存在 {} 路由", request.getRequestURI());
            Routers routers = LoadBalanceTools.getServer(routersList);
            if (routers.getAuthority()) {
                // todo 权限验证
            }
            String redirectIpPort = routers.getIpPort();
            log.info("重定向ipPort: {}", redirectIpPort);
            String redirectUrl = routers.getUrl();
            log.info("重定向url: {}", redirectUrl);
            if (Constant.GET.equals(request.getMethod())) {
                result = HttpTools.httpGet(GpJoinTools.joinGet(redirectIpPort, redirectUrl, request));
            } else if (Constant.POST.equals(request.getMethod()) && request instanceof RequestFacade) {
                result = HttpTools.httpPost(Constant.HTTP + redirectIpPort + redirectUrl, GpJoinTools.joinJsonPost(request));
            } else if (Constant.POST.equals(request.getMethod()) && request instanceof StandardMultipartHttpServletRequest) {
                result = HttpTools.httpPost(Constant.HTTP + redirectIpPort + redirectUrl, GpJoinTools.joinFilePost(request));
            } else {
                // todo 待补充(DELETE PATCH...)
            }
        } catch (Exception e) {
            return error(response, e.getMessage());
        }
        log.info("返回重定向结果: {}", result);
        response.getWriter().append(result);
        return false;
    }

    private boolean error(HttpServletResponse response, String message) throws Exception {
        response.setCharacterEncoding("UTF-8");
        UniResponse uniResponse = UniResponse.builder().message(message).build();
        log.error("errorResponse => status: {}, message: {}", HttpStatus.SC_BAD_REQUEST, message);
        response.getWriter().append(uniResponse.toString());
        return false;
    }
}
