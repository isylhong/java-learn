package com.autumn.learn.java8.designpattern.singleton;

public class HungraySingleton {

    private static HungraySingleton instance = new HungraySingleton();

    private HungraySingleton() {
        // 私有构造方法，避免类在外部被实例化
    }

    public static HungraySingleton getInstance() {
        return instance;
    }
}
