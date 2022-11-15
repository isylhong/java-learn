package com.autumn.learn.java8.designpattern.factory.abstractfactory2;

import com.autumn.learn.java8.designpattern.factory.Phone;

public interface AbstractFactory {
    public Phone productPhone();

    public Computer productComputer();
}
