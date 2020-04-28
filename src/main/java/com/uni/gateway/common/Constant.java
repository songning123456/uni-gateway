package com.uni.gateway.common;

import com.uni.gateway.pojo.Routers;

import java.util.ArrayList;
import java.util.List;

/**
 * @author songning
 * @date 2020/4/27
 * description
 */
public class Constant {

    public static final String HTTP = "http://";

    public static final String QM = "?";

    public static final String EQUAL = "=";

    public static final String AND = "&";

    public static final String GET = "GET";

    public static final String POST = "POST";

    public static final String ZK_ROUTERS = "/uni-register/routers";

    public static List<Routers> routersCache = new ArrayList<>();
}
