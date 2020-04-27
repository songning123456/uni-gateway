package com.uni.gateway.tool;

import java.util.UUID;

/**
 * @author songning
 * @date 2020/4/27
 * description
 */
public class StringTools {
    public static String getRandomUuid(String intervalMark) {
        return UUID.randomUUID().toString().replaceAll("-", intervalMark);
    }
}
