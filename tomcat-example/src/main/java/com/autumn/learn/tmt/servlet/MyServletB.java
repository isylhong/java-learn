package com.autumn.learn.tmt.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class MyServletB extends HttpServlet {
    public MyServletB(){
        super();
        System.out.println("实例化MyServletA...");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        PrintWriter respWriter = resp.getWriter();
        respWriter.print("servletB handle at " + new Date());
    }
}
