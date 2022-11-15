package com.autumn.learn.mvc.service;

import com.autumn.learn.mvc.domain.User;
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
        users.put(202201, new User(202201, "lucy"));
        users.put(202202, new User(202202, "tony"));
    }

    @Override
    public User findUser(User user) {
        return users.get(user.getUserId());
    }
}
