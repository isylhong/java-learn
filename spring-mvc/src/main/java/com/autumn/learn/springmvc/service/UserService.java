package com.autumn.learn.springmvc.service;

import com.autumn.learn.springmvc.domain.User;

/**
 *
 * @author yl
 * @since 2022-11-12 16:41
 */
public interface UserService {
    User findUser(User user);

    int updateUser(User user);
}
