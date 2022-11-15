package com.autumn.learn.mybatis;

import com.autumn.learn.mybatis.domain.Order;
import com.autumn.learn.mybatis.domain.User;
import com.autumn.learn.mybatis.service.OrderService;
import com.autumn.learn.mybatis.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author yl
 * @since 2022-11-14 22:20
 */
public class SpringMybatisTests {

    @Test
    public void testCreateOrder() {
        Order order = new Order();
        order.setOrderNumber("20220101");
        order.setTotal(99.99D);
        order.setUserNumber("202200");
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:com/autumn/learn" +
                "/mybatis/applicationContext.xml");
        OrderService orderService = context.getBean("orderService", OrderService.class);
        orderService.createOrder(order);
    }

    /**
     * 事务AOP测试
     */
    @Test
    public void testTransaction() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:com/autumn/learn" +
                "/mybatis/applicationContext.xml");
        UserService userService = context.getBean("userService", UserService.class);
        User user = new User("202201");
        user.setUsername("张三-transaction");
        try {
            int i = userService.updateUser(user);
            System.out.println("更新 " + i + " 个用户信息");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("用户信息更新失败");
        }
    }

    @Test
    public void testInsert() {
        User user = new User("202202", "李四");

        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:com/autumn/learn" +
                "/mybatis/applicationContext.xml");
        UserService userService = context.getBean("userService", UserService.class);
        int i = userService.addUser(user);
        System.out.println("新增 " + i + " 个用户");
    }

    @Test
    public void testSelect() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:com/autumn/learn" +
                "/mybatis/applicationContext.xml");
        UserService userService = context.getBean("userService", UserService.class);
        User user = new User("202201");
        User result = userService.findUser(user);
        System.out.println(result);
    }
}
