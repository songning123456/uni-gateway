package com.uni.gateway.tool;

import com.alibaba.fastjson.JSONObject;
import com.uni.gateway.common.Constant;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.StringBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * @author songning
 * @date 2020/4/27
 * description
 */
public class GpJoinTools {

    public static String joinGet(String ipPort, String url, HttpServletRequest request) {

        StringBuilder stringBuilder = new StringBuilder().append(Constant.HTTP).append(ipPort).append(url);
        if (!request.getParameterMap().isEmpty()) {
            stringBuilder.append(Constant.QM);
            for (Map.Entry<String, String[]> item : request.getParameterMap().entrySet()) {
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

    public static JSONObject joinJsonPost(HttpServletRequest request) throws Exception {
        JSONObject jsonObject = new JSONObject();
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String postStr;
        while ((postStr = reader.readLine()) != null) {
            stringBuilder.append(postStr);
        }
        jsonObject = JSONObject.parseObject(stringBuilder.toString());
        return jsonObject;
    }

    public static MultipartEntityBuilder joinFilePost(HttpServletRequest request) throws Exception {
        MultipartEntityBuilder meb = MultipartEntityBuilder.create();
        for (Map.Entry<String, String[]> item : request.getParameterMap().entrySet()) {
            String key = item.getKey();
            String[] values = item.getValue();
            for (String val : values) {
                val = URLEncoder.encode(val, "UTF-8");
                meb.addPart(key, new StringBody(val, ContentType.TEXT_PLAIN));
            }
        }
        MultipartHttpServletRequest multipartHttpServletRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
        assert multipartHttpServletRequest != null;
        for (Map.Entry<String, List<MultipartFile>> item : multipartHttpServletRequest.getMultiFileMap().entrySet()) {
            String key = item.getKey();
            List<MultipartFile> values = item.getValue();
            for (MultipartFile val : values) {
                String fileName = val.getOriginalFilename();
                ContentBody files = new ByteArrayBody(val.getBytes(), fileName);
                meb.addPart(key, files);
            }
        }
        return meb;
    }
}
