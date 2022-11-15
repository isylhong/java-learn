package com.autumn.learn.java8.collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class TreeMapTest {
    public static void main(String[] args) {
        Map map = new TreeMap<Integer, String>();
        map.put("1", "aaa");
        map.put("2", "bbb");
        map.put("1", "ccc");

        method4(map);

    }

    // 利用for循环，对Map.Entry键值对集合遍历
    public static void method1(Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String mapKey = entry.getKey();
            String mapValue = entry.getValue();
            System.out.println(mapKey + "：" + mapValue);
        }
    }


    // 利用for循环，对keySet集合遍历
    public static void method2(Map<String, String> map) {
        // 打印键集合
        for (String key : map.keySet()) {
            System.out.println(key);
        }
        // 打印值集合
        for (String value : map.values()) {
            System.out.println(value);
        }

        Collection<String> values = map.values();
    }

    // 利用Iterator迭代器，对Map.Entry键值对集合遍历
    public static void method3(Map<String, String> map) {
        Iterator<Map.Entry<String, String>> entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println(key + ":" + value);
        }
    }

    // 利用Iterator迭代器，对键的keySet集合遍历
    public static void method4(Map<String, String> map) {
        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            String value = map.get(key);
            System.out.println(key + "=>" + value);
        }
    }

}
