package com.example.yy.fast_json.fast_json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.*;

public class JsonUtil {

    /**
     * 功能描述：把JSON数据转换成指定的java对象
     * @param jsonData JSON数据
     * @param clazz 指定的java对象
     * @return 指定的java对象
     */
    public static <T> T jsonToObj(String jsonData, Class<T> clazz) {
        return JSON.parseObject(jsonData, clazz);
    }

    /**
     * 功能描述：把java对象转换成JSON数据
     * @param object java对象
     * @return JSON数据
     */
    public static String objToJson(Object object) {
        return JSON.toJSONString(object);
    }

    /**
     * 功能描述：把JSON数据转换成指定的java对象列表
     * @param jsonData JSON数据
     * @param clazz 指定的java对象
     * @return List<T>
     */
    public static <T> List<T> jsonToList(String jsonData, Class<T> clazz) {
        return JSON.parseArray(jsonData, clazz);
    }

    /* 功能描述：将List<T>转换为json字符串
    * @param list
    * @return: String
    **/
    public static <T> String listToJson(List<T> list){
        return JSON.toJSONString(list);
    }

    /* 功能描述：将JSON数据转换成Java对象Map
    * @param jsonData
    * @param clazz
    * @return:
    **/
    public static <T> Map<String,T> jsonToMap(String jsonData,Class<T> clazz){
        JSONObject jsonObject = JSONObject.parseObject(jsonData);
        return (Map<String,T>)jsonObject;
    }

    /* 功能描述：将Map<String,T>转换为JSON字符串
    * @param map
    * @return:
    **/
    public static <T> String mapToJson(Map<String,T> map){
        return JSON.toJSONString(map);
    }

    /**
     * 功能描述：把JSON数据转换成较为复杂的List<Map<String, Object>>
     * @param jsonData JSON数据
     * @return List<Map<String, Object>>
     */
    public static List<Map<String, Object>> jsonToListMap(String jsonData) {
        return JSON.parseObject(jsonData, new TypeReference<List<Map<String, Object>>>() {});
    }
}
