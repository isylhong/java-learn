package com.autumn.learn.java8.designpattern.visitor;

/**
 * 快车表
 * 具体元素（ConcreteElement）：实现抽象元素角色提供的 accept() 操作，其方法体通常都是 visitor.visitXX(this) ，另外具体元素中可能还包含本身业务逻辑的相关操作。
 *
 * @author yl
 * @since 2022-11-15 19:52
 */
public class KTrainTicket implements Ticket {

    @Override
    public void accept(Visitor visitor) {
        visitor.buyTicket(this);
    }

    public int getKTrainTicketPrice() {
        return 100;
    }
}
