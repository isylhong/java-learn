package com.autumn.learn.java8.designpattern.proxy;

public class CollectFeeProxy implements CollectFee {

    private CollectFee collectFee;

    public CollectFeeProxy(CollectFee collectFee) {
        this.collectFee = collectFee;
    }

    @Override
    public void doCollect() {
        if (collectFee==null){
            System.out.println("未找到目标类");
        }

        preCollect();
        collectFee.doCollect();
        afterCollect();
    }

    public void preCollect() {
        System.out.println("收班费前工作，统计人数");
    }

    public void afterCollect() {
        System.out.println("收费后工作，统计并验证总费用");
    }
}
