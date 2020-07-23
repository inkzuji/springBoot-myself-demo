package com.example.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * json转换
 *
 * @author Ink足迹
 * @create 2020-06-02 14:11
 **/
public class JacksonUtils {
    private final static ObjectMapper mapper = new ObjectMapper();

    static {
        // 对于空的对象转json的时候不抛出错误
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // 允许属性名称没有引号
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // 允许单引号
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        // 在反序列化时忽略在 json 中存在但 Java 对象不存在的属性
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 设置输出时包含属性的风格
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * 序列化，将对象转化为json字符串
     *
     * @param data 待转对象
     * @return 返回结果
     */
    public static String json2Str(Object data) {
        if (Objects.isNull(data)) {
            return null;
        }
        String json = null;
        try {
            json = mapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 反序列化，将json字符串转化为对象
     *
     * @param json   待转对象
     * @param tClass 名称
     * @param <T>    类型
     * @return 返回结果
     */
    public static <T> T str2json(String json, Class<T> tClass) {
        T t = null;
        try {
            t = mapper.readValue(json, tClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 将对象转化为map
     *
     * @param data 参数
     * @return 返回map
     */
    public static Map<String, String> bean2Map(Object data) {
        Map<String, String> map = null;

        try {
            String json = mapper.writeValueAsString(data);
            map = mapper.readValue(json, new TypeReference<HashMap<String, String>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 将对象转化为map
     *
     * @param json 参数
     * @return 返回map
     */
    public static HashMap<String, Object> json2Map(String json) {
        HashMap<String, Object> map = null;
        try {
            map = mapper.readValue(json, new TypeReference<HashMap<String, Object>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 实体类相互转换
     *
     * @param data          待转换数据
     * @param typeReference 转换类型
     * @return 返回结果
     */
    public static <T> T bean2Bean(Object data, TypeReference<T> typeReference) {
        try {
            String json = mapper.writeValueAsString(data);
            return mapper.readValue(json, typeReference);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * string 转 实体类
     *
     * @param json          待转换数据
     * @param typeReference 转换类型
     * @return 返回结果
     */
    public static <T> T json2Bean(String json, TypeReference<T> typeReference) {
        try {
            return mapper.readValue(json, typeReference);
        } catch (IOException e) {
            return null;
        }
    }

}
