package com.autumn.learn.java8.designpattern.factory.simplefactory;

import com.autumn.learn.java8.designpattern.factory.Phone;

public class Consumer {
    public static void main(String[] args) {
        SimpleFactory factory = new SimpleFactory();
        Phone phone = factory.productPhone("XiaoMi");
        phone.listFunction();
    }
}
