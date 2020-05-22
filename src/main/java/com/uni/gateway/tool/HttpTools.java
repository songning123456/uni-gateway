package com.uni.gateway.tool;

import com.alibaba.fastjson.JSONObject;
import com.uni.gateway.pojo.UniResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author songning
 * @date 2020/4/27
 * description
 */
@Slf4j
public class HttpTools {

    public static String httpGet(String url) {
        String result;
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = httpClient.execute(httpGet);
            result = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            log.error("httpGet fail: {}", e.getMessage());
            result = UniResponse.builder().status(HttpStatus.SC_BAD_REQUEST).message(e.getMessage()).build().toString();
        }
        return result;
    }

    public static String httpPost(String url, JSONObject params) {
        String result;
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        if (params != null) {
            //解决中文乱码问题
            StringEntity entity = new StringEntity(params.toString(), "utf-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
        }
        try {
            HttpResponse response = httpClient.execute(httpPost);
            result = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            log.error("httpJsonPot fail: {}", e.getMessage());
            result = UniResponse.builder().status(HttpStatus.SC_BAD_REQUEST).message(e.getMessage()).build().toString();
        }
        return result;
    }

    public static String httpPost(String url, MultipartEntityBuilder meb) {
        String result;
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(meb.build());
        try {
            HttpResponse response = httpClient.execute(httpPost);
            result = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            log.error("httpFilePot fail: {}", e.getMessage());
            result = UniResponse.builder().status(HttpStatus.SC_BAD_REQUEST).message(e.getMessage()).build().toString();
        }
        return result;
    }
}
