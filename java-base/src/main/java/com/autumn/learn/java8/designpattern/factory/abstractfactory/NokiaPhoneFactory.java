package com.autumn.learn.java8.designpattern.factory.abstractfactory;

import com.autumn.learn.java8.designpattern.factory.NokiaPhone;
import com.autumn.learn.java8.designpattern.factory.Phone;


public class NokiaPhoneFactory implements PhoneFactory{

    @Override
    public Phone productPhone() {
        return new NokiaPhone();
    }
}
