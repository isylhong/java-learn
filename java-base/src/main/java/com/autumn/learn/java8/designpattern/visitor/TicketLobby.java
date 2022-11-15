package com.autumn.learn.java8.designpattern.visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * 售票厅
 * 对象结构（Object Structure）：一个包含元素角色的容器，提供让访问者对象遍历容器中的所有元素的方法，通常由 List、Set、Map 等聚合类实现。本例中的动物园就可抽象成一个对象结构。
 *
 * @author yl
 * @since 2022-11-15 19:54
 */
public class TicketLobby {
    List<Ticket> tickets = new ArrayList<>();

    public TicketLobby() {
    }

    public TicketLobby(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public void accept(Visitor visitor) {
        if (tickets.size() == 0) {
            return;
        }
        for (Ticket ticket : tickets) {
            ticket.accept(visitor);
        }
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }
}
