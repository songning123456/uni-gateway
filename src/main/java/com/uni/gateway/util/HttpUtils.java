package com.uni.gateway.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * @author songning
 * @date 2020/4/27
 * description
 */
@Slf4j
public class HttpUtils {

    public static HttpResponse getQuery(String url) {
        HttpResponse response = null;
        //获取httpclient对象
        HttpClient httpClient = HttpClientBuilder.create().build();
        //获取get请求对象
        HttpGet httpGet = new HttpGet(url);
        try {
            //发起请求
            response = httpClient.execute(httpGet);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getQuery fail: url => {}", url);
        }
        return response;
    }

    public static HttpResponse postQuery(String url, String params) {
        HttpResponse response = null;
        //获取httpclient对象
        HttpClient httpClient = HttpClientBuilder.create().build();
        //获取请求对象
        HttpPost httpPost = new HttpPost(url);
        try {
            //把传入进来的结构树封装
            httpPost.setEntity(new StringEntity(params, "utf-8"));
            //执行一个post请求
            response = httpClient.execute(httpPost);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("postQuery fail: url => {}, params => {}", url, params);
        }
        return response;
    }
}
