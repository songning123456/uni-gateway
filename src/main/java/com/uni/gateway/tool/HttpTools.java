package com.uni.gateway.tool;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 * @author songning
 * @date 2020/4/27
 * description
 */
@Slf4j
public class HttpTools {

    public static String httpGet(String url) throws Exception {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = httpClient.execute(httpGet);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            return EntityUtils.toString(response.getEntity(), "utf-8");
        } else {
            log.error("~~~GET请求连接失败: {}~~~", response.getStatusLine().getStatusCode());
            throw new Exception("httpGet fail:" + response.getStatusLine().getStatusCode());
        }
    }

    public static String httpPost(String url, JSONObject params) throws Exception {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        if (params != null) {
            //解决中文乱码问题
            StringEntity entity = new StringEntity(params.toString(), "utf-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
        }
        HttpResponse response = httpClient.execute(httpPost);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            return EntityUtils.toString(response.getEntity(), "utf-8");
        } else {
            log.error("~~~POST请求连接失败: {}~~~", response.getStatusLine().getStatusCode());
            throw new Exception("httpPost fail:" + response.getStatusLine().getStatusCode());
        }
    }
}
