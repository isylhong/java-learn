package com.autumn.learn.java8.designpattern.factory.abstractfactory2;

public class XiaoMiComputer implements Computer{

    @Override
    public void listConfiguration() {
        System.out.println("小米电脑\t四核八线程");
    }
}
