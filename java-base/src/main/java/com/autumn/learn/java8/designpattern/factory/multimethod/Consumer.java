package com.autumn.learn.java8.designpattern.factory.multimethod;

import com.autumn.learn.java8.designpattern.factory.Phone;

public class Consumer {
    public static void main(String[] args) {
        MultiMethodFactory factory = new MultiMethodFactory();
        Phone phone = factory.productHuaWeiPhone();
        phone.listFunction();
    }
}
