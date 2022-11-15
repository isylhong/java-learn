package com.autumn.learn.java8.designpattern.visitor;

/**
 * 学生
 * 具体的访问者（ConcreteVisitor）：实现抽象访问者角色中声明的各个访问操作，确定访问者访问一个元素时该做什么。
 *
 * @author yl
 * @since 2022-11-15 19:53
 */
public class StudentVisitor implements Visitor {
    @Override
    public void buyTicket(KTrainTicket kTrainTicket) {
        float price = kTrainTicket.getKTrainTicketPrice() * 0.5F;
        System.out.println("快车票，学生5折！" + price + "元");
    }

    @Override
    public void buyTicket(GTrainTicket gTrainTicket) {
        float price = gTrainTicket.getGTrainTicketPrice() * 0.75F;
        System.out.println("高铁票，学生7.5折！" + price + "元");
    }
}