package com.uni.gateway.tool;

import com.uni.gateway.pojo.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;

/**
 * @author songning
 * @date 2020/4/28
 * description
 */
@Slf4j
public class ResponseTools {

    public static boolean error(HttpServletResponse response, String phase, int status, String message) throws Exception {
        response.setCharacterEncoding("UTF-8");
        ErrorResponse errorResponse = ErrorResponse.builder().phase(phase).status(status).message(message).build();
        log.error("errorResponse => status: {}, phase: {}, message: {}", HttpStatus.SC_BAD_REQUEST, phase, message);
        response.getWriter().append(errorResponse.toString());
        return false;
    }

    public static boolean error(HttpServletResponse response, String phase, String message) throws Exception {
        response.setCharacterEncoding("UTF-8");
        ErrorResponse errorResponse = ErrorResponse.builder().phase(phase).message(message).build();
        log.error("errorResponse => status: {}, phase: {}, message: {}", HttpStatus.SC_BAD_REQUEST, phase, message);
        response.getWriter().append(errorResponse.toString());
        return false;
    }
}
