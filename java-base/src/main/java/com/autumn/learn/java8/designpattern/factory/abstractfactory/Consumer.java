package com.autumn.learn.java8.designpattern.factory.abstractfactory;

import com.autumn.learn.java8.designpattern.factory.Phone;

public class Consumer {
    public static void main(String[] args) {
        PhoneFactory phoneFactory = new NokiaPhoneFactory();
//        PhoneFactory phoneFactory = new NokiaPlusPhoneFactory(); // 功能增强版Nokia

        Phone phone = phoneFactory.productPhone();
        phone.listFunction();
    }


}
