package com.autumn.learn.mybatis.dao;

import com.autumn.learn.mybatis.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @author yl
 * @since 2022-11-14 22:18
 */
public interface UserDao {
    List<User> listUser();

    User findUser(@Param("user") User user);

    int addUser(@Param("user") User user);

    int updateUser(@Param("user") User user);

    int deleteUser(@Param("user") User user);
}
