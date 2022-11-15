package com.autumn.learn.java8.designpattern.factory.abstractfactory2;

import com.autumn.learn.java8.designpattern.factory.Phone;
import com.autumn.learn.java8.designpattern.factory.XiaoMiPhone;

public class XiaoMiFactory implements AbstractFactory {

    @Override
    public Phone productPhone() {
        return new XiaoMiPhone();
    }

    @Override
    public Computer productComputer() {
        return new XiaoMiComputer();
    }
}
