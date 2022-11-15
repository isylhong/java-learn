package com.autumn.learn.mybatis.dao;

import com.autumn.learn.mybatis.domain.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @author yl
 * @since 2022-11-14 22:19
 */
public interface OrderDao {
    int createOrder(@Param("order") Order order);

    int deleteOrder(@Param("order") Order order);

    List<Order> listOrder(@Param("order") Order order);
}
