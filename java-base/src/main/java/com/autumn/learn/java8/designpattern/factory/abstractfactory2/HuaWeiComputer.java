package com.autumn.learn.java8.designpattern.factory.abstractfactory2;

public class HuaWeiComputer implements Computer {

    @Override
    public void listConfiguration() {
        System.out.println("华为电脑\t六核十二线程");
    }

}
