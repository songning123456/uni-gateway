package com.uni.gateway.zookeeper;

import com.alibaba.fastjson.JSON;
import com.uni.gateway.common.Constant;
import com.uni.gateway.pojo.Routers;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author songning
 * @date 2020/4/24
 * description
 */
@Component
@Slf4j
public class ZKCuratorClient {
    /**
     * zk客户端
     */
    private CuratorFramework curatorFramework = null;

    @Value("${zookeeper.url}")
    private String zkUrl;
    @Value("${zookeeper.namespace}")
    private String zkNamespace;
    @Value("${zookeeper.node}")
    private String zkNode;

    public void init() {
        if (curatorFramework != null) {
            return;
        }
        // 重试策略
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 5);
        // 创建zk客户端
        curatorFramework = CuratorFrameworkFactory.builder().connectString(zkUrl).sessionTimeoutMs(10000).retryPolicy(retryPolicy).namespace(zkNamespace).build();
        // 启动客户端
        curatorFramework.start();
        try {
            addChildWatch(zkNode);
        } catch (Exception e) {
            log.error("addChildWatch fail: {}", e.getMessage());
        }
    }

    /**
     * 事件监听
     *
     * @param nodePath
     * @throws Exception
     */
    private void addChildWatch(String nodePath) throws Exception {

        final PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework, nodePath, true);
        pathChildrenCache.start();
        pathChildrenCache.getListenable().addListener((client, event) -> {
            try {
                if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_UPDATED) || event.getType().equals(PathChildrenCacheEvent.Type.CHILD_ADDED)) {
                    log.info("节点监听类型: {}", event.getType());
                    String path = event.getData().getPath();
                    log.info("zk节点路径: {}", path);
                    String operatorObjStr = new String(event.getData().getData());
                    log.info("zk传递值: {}", operatorObjStr);
                    if (Constant.ZK_ROUTERS.equals(path)) {
                        List<Routers> routersList = JSON.parseArray(operatorObjStr, Routers.class);
                        synchronized (this) {
                            Constant.ROUTERS_CACHE.clear();
                            for (Routers routers : routersList) {
                                if (!Constant.ROUTERS_CACHE.contains(routers)) {
                                    Constant.ROUTERS_CACHE.add(routers);
                                }
                            }
                        }
                    }
                } else {
                    log.info("节点监听类型(other): {}", event.getType());
                    if (event.getData() != null) {
                        String path = event.getData().getPath();
                        log.info("zk节点路径(other): {}", path);
                        String operatorObjStr = new String(event.getData().getData());
                        log.info("zk传递值(other): {}", operatorObjStr);
                    }
                }
            } catch (Exception e) {
                log.error("监听节点失败: {}", e.getMessage());
            }
        });
    }
}
