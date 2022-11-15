package com.autumn.learn.tmt.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author yl
 * @since 2022-11-12 20:54
 */
public class MyListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("执行自定义ServletContextListener.contextInitialized()方法。。。");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("执行自定义ServletContextListener.contextDestroyed()方法。。。");
    }
}
