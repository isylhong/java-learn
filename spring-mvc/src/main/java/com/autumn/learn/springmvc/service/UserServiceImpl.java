package com.autumn.learn.springmvc.service;

import com.autumn.learn.springmvc.domain.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 *
 * @author yl
 * @since 2022-11-12 16:42
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    private final HashMap<Integer, User> users = new HashMap<>();

    {
        users.put(202201, new User(202201, "张三"));
        users.put(202202, new User(202202, "李四"));
        users.put(202203, new User(202203, "王五"));
    }

    @Override
    public User findUser(User user) {
        return users.get(user.getUserId());
    }

    @Override
    public int updateUser(User user) {
        if (user.getUserId() == null)
            return 0;
        User result = users.get(user.getUserId());
        return result == null ? 0 : 1;
    }
}
