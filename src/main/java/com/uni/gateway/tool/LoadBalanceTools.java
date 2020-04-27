package com.uni.gateway.tool;

import com.uni.gateway.pojo.CommonRouters;

import java.util.*;

/**
 * @author songning
 * @date 2020/4/27
 * description
 */
public class LoadBalanceTools {

    public static String getServer(Map<String, Integer> ipMap) {
        // 重建一个Map，避免服务器的上下线导致的并发问题
        Map<String, Integer> serverMap = new HashMap<>(1);
        serverMap.putAll(ipMap);

        // 取得Ip地址List
        Set<String> keySet = serverMap.keySet();
        Iterator<String> iterator = keySet.iterator();

        List<String> serverList = new ArrayList<>();
        while (iterator.hasNext()) {
            String server = iterator.next();
            int weight = serverMap.get(server);
            for (int i = 0; i < weight; i++) {
                serverList.add(server);
            }
        }
        Random random = new Random();
        int randomPos = random.nextInt(serverList.size());
        return serverList.get(randomPos);
    }

    public static CommonRouters.Routers getServer(List<CommonRouters.Routers> routersList) {
        Iterator<CommonRouters.Routers> iterator = routersList.iterator();
        List<CommonRouters.Routers> serverList = new ArrayList<>();
        while (iterator.hasNext()) {
            CommonRouters.Routers routers = iterator.next();
            int weight = routers.getWeight();
            for (int i = 0; i < weight; i++) {
                serverList.add(routers);
            }
        }
        Random random = new Random();
        int randomPos = random.nextInt(serverList.size());
        return serverList.get(randomPos);
    }
}
