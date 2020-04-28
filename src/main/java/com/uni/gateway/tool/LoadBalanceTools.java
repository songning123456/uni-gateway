package com.uni.gateway.tool;

import com.uni.gateway.pojo.Routers;

import java.util.*;

/**
 * @author songning
 * @date 2020/4/27
 * description
 */
public class LoadBalanceTools {

    public static Routers getServer(List<Routers> params) {
        List<Routers> copy = new ArrayList<>(params);
        Iterator<Routers> iterator = copy.iterator();
        List<Routers> serverList = new ArrayList<>();
        while (iterator.hasNext()) {
            Routers routers = iterator.next();
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
