package com.autumn.learn.mybatis.service;

import com.autumn.learn.mybatis.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @author yl
 * @since 2022-11-14 22:21
 */
public interface UserService {
    List<User> listUser();

    User findUser(User user);

    int addUser(User user);

    int updateUser(User user);

    int deleteUser(User user);
}
