package com.autumn.learn.java8.designpattern.factory.abstractfactory;

import com.autumn.learn.java8.designpattern.factory.Phone;

public class NokiaPlusPhoneFactory implements PhoneFactory{
    @Override
    public Phone productPhone() {
        return new NokiaPlusPhone();
    }
}
