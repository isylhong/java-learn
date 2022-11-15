package com.autumn.learn.java8.designpattern.visitor;

import org.junit.Test;

/**
 * 观察者模式
 *
 * @author yl
 * @since 2022-11-15 19:50
 */
public class VisitorTest {
    @Test
    public void testVisitor() {
        TicketLobby ticketLobby = new TicketLobby();
        ticketLobby.addTicket(new KTrainTicket());
        ticketLobby.addTicket(new GTrainTicket());
        Visitor studentVisitor = new StudentVisitor();
        ticketLobby.accept(studentVisitor); // 学生票

        System.out.println("----------------");
        Visitor adultVisitor = new AdultVisitor();
        ticketLobby.accept(adultVisitor); // 成人票
    }
}
