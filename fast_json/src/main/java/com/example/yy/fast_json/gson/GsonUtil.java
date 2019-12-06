package com.example.yy.fast_json.gson;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class GsonUtil {
    private static Gson gson;

    private GsonUtil(){}

    static {
        if (gson == null){
            gson = new Gson();
        }
    }

    /*
    * 将对象转换为json字符串
    * */
    public static String toJsonString(Object object){
        String jsonString = null;
        if (gson != null){
            jsonString = gson.toJson(object);
        }
        return jsonString;
    }

    /*
    * 将json字符串转换为对象
    * */
    public static <T> T jsonToObject(String jsonString,Class<T> cls){
        T t = null;
        if (gson != null){
            t = gson.fromJson(jsonString,cls);
        }
        return t;
    }

    /*
    * 将json字符串转换为List,List中的元素为Object
    * */
    public static <T> List<T> jsonToList(String jsonString,Class<T> cls){
        List<T> list = null;
        if(gson != null){
            list = gson.fromJson(jsonString,new TypeToken<List<T>>(){}.getType());
        }
        return list;
    }

    /*
    * 将List转换为jsonString
    * */
    public static <T> String listToJson(List<T> list){
        String jsonString = null;
        if(gson != null){
            jsonString = gson.toJson(list);
        }
        return jsonString;
    }


    /*
    * 将json字符串转换为Map
    * K为jsonString , V为Object
    * */
    public static <T> Map<String,T> jsonToMap(String jsonString, Class<T> cls){
        Map<String,T> map = null;
        if(gson != null){
            map = gson.fromJson(jsonString,new TypeToken<Map<String,T>>(){}.getType());
        }
        return map;
    }

    /*
    * 将Map转换为jsonString
    * */
    public static <T> String mapToJson(Map<String,T> map){
        String jsonString = null;
        if(gson != null){
            jsonString = gson.toJson(map);
        }
        return jsonString;
    }

    /*
    * 将json字符串转换为Set
    * */
    public static <T> Set<T> jsonToSet(String jsonString,Class<T> cls){
        Set<T> set = null;
        if(gson != null){
            set = gson.fromJson(jsonString,new TypeToken<Set<T>>(){}.getType());
        }
        return set;
    }

    /*
    * 将Set转换为jsonString
    * */
    public static <T> String setToJson(Set<T> set){
        String jsonString = null;
        if(gson != null){
            jsonString = gson.toJson(set);
        }
        return jsonString;
    }

    /*
    * 将jsonString转换为List，List中的元素为Map<String,T>
    * */
    public static <T> List<Map<String,T>> complexJsonToList(String jsonString,Class<T> cls){
        List<Map<String,T>> list = null;
        if (gson != null){
            list = gson.fromJson(jsonString,new TypeToken<Map<String,T>>(){}.getType());
        }
        return list;
    }

    /*
    * 将List转换为jsonString，List中的元素为Map<String,T>
    * */
    public static <T> String complexListToJson(List<Map<String,T>> list){
        String jsonString = null;
        if (gson != null){
            jsonString = gson.toJson(list);
        }
        return jsonString;
    }


}
