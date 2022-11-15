package com.autumn.learn.java8.designpattern.proxy;

public class Client {
    public static void main(String[] args) {
        System.out.println("**********未使用代理收费**********");
        CollectFee collectFee = new CollectFeeImpl();
        collectFee.doCollect();

        System.out.println("\n**********使用代理收费**********");
        CollectFee proxy = new CollectFeeProxy(collectFee);
        proxy.doCollect();
    }
}
