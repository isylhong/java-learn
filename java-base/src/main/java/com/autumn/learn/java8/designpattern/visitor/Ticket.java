package com.autumn.learn.java8.designpattern.visitor;

/**
 * 抽象元素（Element）：声明一个包含接受操作 accept() 的接口，其参数为访问者对象（游客）。
 *
 * @author yl
 * @since 2022-11-15 19:51
 */
public interface Ticket {
    public void accept(Visitor visitor);
}

