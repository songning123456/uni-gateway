package com.uni.gateway.tool;

import java.util.*;

/**
 * @author songning
 * @date 2020/4/27
 * description
 */
public class LoadBalanceTools {

    public static Map getServer(List<Map> params) {
        List<Map> copy = new ArrayList<>(params);
        Iterator<Map> iterator = copy.iterator();
        List<Map> serverList = new ArrayList<>();
        while (iterator.hasNext()) {
            Map routers = iterator.next();
            int weight = Integer.parseInt(routers.get("weight").toString());
            for (int i = 0; i < weight; i++) {
                serverList.add(routers);
            }
        }
        Random random = new Random();
        int randomPos = random.nextInt(serverList.size());
        return serverList.get(randomPos);
    }
}
