package com.autumn.learn.java8.designpattern.visitor;

/**
 * 抽象的访问者（Visitor）：访问具体元素的接口，为每个具体元素类对应一个访问操作 visitXX() ，其参数为某个具体的元素。
 *
 * @author yl
 * @since 2022-11-15 19:52
 */
public interface Visitor {
    public void buyTicket(KTrainTicket kTrainTicket);

    public void buyTicket(GTrainTicket gTrainTicket);
}
