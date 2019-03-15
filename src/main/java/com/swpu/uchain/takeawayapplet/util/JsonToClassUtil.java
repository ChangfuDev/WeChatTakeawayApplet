package com.swpu.uchain.takeawayapplet.util;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @ClassName JsonToClassUtil
 * @Author hobo
 * @Date 19-3-14 下午3:13
 * @Description
 **/
public class JsonToClassUtil {

    public static ObjectMapper objectMapper;

    public static <T> T objectFromJsonStr(String content,Class<T> valueType) throws Exception {
        T obj = objectMapper.readValue(content, valueType);
        return obj;
    };

}
