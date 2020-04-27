package com.uni.gateway.tool;

import com.alibaba.fastjson.JSONObject;
import com.uni.gateway.common.Constant;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * @author songning
 * @date 2020/4/27
 * description
 */
public class GpJoinTools {

    public static String joinGet(String ip, String url, Map<String, String[]> params) {
        StringBuilder stringBuilder = new StringBuilder().append(Constant.HTTP).append(ip).append(url);
        if (!params.isEmpty()) {
            stringBuilder.append(Constant.QM);
            for (Map.Entry<String, String[]> item : params.entrySet()) {
                String key = item.getKey();
                String[] value = item.getValue();
                StringBuilder sb = new StringBuilder();
                for (String val : value) {
                    sb.append(val);
                }
                stringBuilder.append(key).append(Constant.EQUAL).append(sb.toString()).append(Constant.AND);
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

    public static JSONObject joinPost(HttpServletRequest request) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String postStr = "";
        while ((postStr = reader.readLine()) != null) {
            stringBuilder.append(postStr);
        }
        return JSONObject.parseObject(stringBuilder.toString());
    }
}