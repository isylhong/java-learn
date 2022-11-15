package com.autumn.learn.java8.designpattern.proxy;

public class CollectFeeImpl implements CollectFee{
    @Override
    public void doCollect() {
        System.out.println("开始收集班费！");
    }
}
