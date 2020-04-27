package com.uni.gateway.pojo;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * @author: songning
 * @date: 2020/4/27 21:45
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {

    @NotNull
    private Integer status;

    @NotNull
    private String message;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this, SerializerFeature.WriteMapNullValue);
    }
}
