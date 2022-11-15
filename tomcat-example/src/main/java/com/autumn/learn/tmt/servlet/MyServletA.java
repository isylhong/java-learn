package com.autumn.learn.tmt.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class MyServletA extends HttpServlet {
    public MyServletA() {
        super();
        System.out.println("实例化MyServletA...");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter respWriter = resp.getWriter();
        respWriter.println("do filter at " + req.getAttribute("filterTime"));
        respWriter.print("servletA handle at " + new Date());
    }
}
