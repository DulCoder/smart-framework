package com.web.framework.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * json工具类
 *
 * Created by zhengxianyou on 2018/3/26 0026.
 */
public final class JsonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();


    /**
     * 把 POJO 转为 JSON
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String toJson(T obj){
        String json;

        try {
            json = OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOGGER.error("convert POJO to JSON failure", e);
            throw new RuntimeException(e);
        }

        return json;
    }

    /**
     * 把 JSON 转为 POJO
     *
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T formJson(String json,Class<T> type){
         T pojo;

        try {
            pojo = OBJECT_MAPPER.readValue(json,type);
        } catch (Exception e) {
            LOGGER.error("convert JSON to POJO failure", e);
            throw new RuntimeException(e);
        }

        return pojo;
    }

}
