package com.autumn.learn.mybatis.service;

import com.autumn.learn.mybatis.domain.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @author yl
 * @since 2022-11-14 22:21
 */
public interface OrderService {
    List<Order> listOrder(Order order);

    int createOrder(Order order);

    int deleteOrder(Order order);
}
