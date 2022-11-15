package com.autumn.learn.java8.designpattern.factory.multistaticmethod;

import com.autumn.learn.java8.designpattern.factory.Phone;
public class Consumer {
    public static void main(String[] args) {
        Phone phone = MultiStaticMethodFactory.productNokiaPhone();
        phone.listFunction();
    }
}
