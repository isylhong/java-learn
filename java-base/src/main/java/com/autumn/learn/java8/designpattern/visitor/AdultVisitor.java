package com.autumn.learn.java8.designpattern.visitor;

/**
 * 成年人
 * 具体的访问者（ConcreteVisitor）：实现抽象访问者角色中声明的各个访问操作，确定访问者访问一个元素时该做什么。
 *
 * @author yl
 * @since 2022-11-15 19:52
 */
public class AdultVisitor implements Visitor {
    @Override
    public void buyTicket(KTrainTicket kTrainTicket) {
        float price = kTrainTicket.getKTrainTicketPrice();
        System.out.println("成人快车票，无打折！" + price + "元");
    }

    @Override
    public void buyTicket(GTrainTicket gTrainTicket) {
        float price = gTrainTicket.getGTrainTicketPrice();
        System.out.println("成人高铁票，无打折！" + price + "元");
    }
}