package com.example.yy.fast_json.gson;

import android.os.SystemClock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GsonTest {
    private static String fatherJsonString = "{\"age\":36,\"child\":{\"age\":12,\"grade\":88,\"name\":\"Mike\"},\"name\":\"Bob\"}";
    private static String childJsonString = "{\"age\":12,\"grade\":88,\"name\":\"Mike\"}";
    private static String fatherListJsonString =
            "[{\"age\":36," +
            "\"child\":{\"age\":12,\"grade\":88,\"name\":\"Mike\"}," +
            "\"name\":\"Bob\"}," +
                    "{\"age\":35," +
                    "\"child\":{\"age\":15,\"grade\":90,\"name\":\"Brown\"}," +
                    "\"name\":\"Alice\"}]";
    private static String childListJsonString = "[{\"age\":12,\"grade\":88,\"name\":\"Mike\"},{\"age\":15,\"grade\":90,\"name\":\"Brown\"}]";

    private static String fatherMapJsonString =
            "{\"1\":{\"name\":\"child1\",\"age\":22,\"grade\":80}," +
            "\"2\":{\"name\":\"child2\",\"age\":23,\"grade\":85}}";
    private static String childMapJsonString =
            "{\"1\":{\"name\":\"father1\",\"age\":35,\"child\":{\"name\":\"father1_son\",\"age\":17,\"grade\":87}}," +
            "\"2\":{\"name\":\"father2\",\"age\":33,\"child\":{\"name\":\"father2_son\",\"age\":19,\"grade\":85}}}";

    private static Father father = new Father("Mike",31,new Child("Mikeson",13,99));
    private static Child child = new Child("Mike",12,88);

    private static List<Father> fatherList;
    private static List<Child> childList;

    private static Map<String,Father> fatherMap;
    private static Map<String,Child> childMap;

    static {
        fatherList = new ArrayList<>();
        fatherList.add(new Father("Bob",36,new Child("Mike",12,88)));
        fatherList.add(new Father("Alice",35,new Child("Brown",15,90)));

        childList = new ArrayList<>();
        childList.add(new Child("Mike",12,88));
        childList.add(new Child("Brown",15,90));

        childMap = new HashMap<>();
        childMap.put("1",new Child("child1",22,80));
        childMap.put("2",new Child("child2",23,85));


        fatherMap = new HashMap<>();
        fatherMap.put("1",new Father("father1",35,new Child("father1_son",17,87)));
        fatherMap.put("2",new Father("father2",33,new Child("father2_son",19,85)));
    }

    public static void main(String[] args){

        //把json字符串转换为对象
        System.out.println("把json字符串转换为对象");
        Father father = GsonUtil.jsonToObject(fatherJsonString,Father.class);
        Child child = GsonUtil.jsonToObject(childJsonString,Child.class);
        System.out.println(father);
        System.out.println(child);

        //把json字符串转换为List
        System.out.println("把json字符串转换为List");
        List<Father> fatherList = GsonUtil.jsonToList(fatherListJsonString,Father.class);
        List<Child> childList = GsonUtil.jsonToList(childListJsonString,Child.class);
        System.out.println(fatherList);
        System.out.println(childList);

        //把对象转换为json字符串
        System.out.println("把对象转换为json字符串");
        String fatherString = GsonUtil.toJsonString(father);
        String childString = GsonUtil.toJsonString(child);

        System.out.println(fatherString);
        System.out.println(childString);

        /*
        * 不能用foreach
        * */
        System.out.println("List中的内容");
        for(int i = 0;i<fatherList.size();i++){
            System.out.println(fatherList.get(i));
        }

        for(int i = 0;i<childList.size();i++){
            System.out.println(childList.get(i));
        }

        //将List转换为jsonString
        System.out.println("将List转换为jsonString");
        String fatherListJson = GsonUtil.listToJson(fatherList);
        System.out.println(fatherListJson);
        System.out.println(fatherListJsonString);
        System.out.println(fatherListJson.equals(fatherListJsonString));

        String childListJson = GsonUtil.listToJson(childList);
        System.out.println(childListJson);
        System.out.println(childListJsonString);
        System.out.println(childListJson.equals(childListJsonString));

        //将Map转换为jsonString
        System.out.println("将Map转换为jsonString");
        String childMapString = GsonUtil.mapToJson(childMap);
        System.out.println(childMapString);

        String fatherMapString = GsonUtil.mapToJson(fatherMap);
        System.out.println(fatherMapString);


        //将jsonString转换为Map
        System.out.println("将jsonString转换为Map");
        Map<String,Father> fatherMap = GsonUtil.jsonToMap(fatherMapJsonString,Father.class);
        Map<String,Child> childMap = GsonUtil.jsonToMap(childMapJsonString,Child.class);
        System.out.println(fatherMap);
        System.out.println(childMap);

    }
}
