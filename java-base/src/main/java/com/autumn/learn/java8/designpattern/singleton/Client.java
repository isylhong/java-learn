package com.autumn.learn.java8.designpattern.singleton;

public class Client {
    public static void main(String[] args) {
        System.out.println("*****饿汉模式单例*******");
        HungraySingleton hungraySingleton = HungraySingleton.getInstance();
        System.out.println(hungraySingleton);

        System.out.println("\n*****懒汉模式单例*******");
        LazySingleton lazySingleton = LazySingleton.getInstance();
        System.out.println(lazySingleton.getStr());
    }
}
