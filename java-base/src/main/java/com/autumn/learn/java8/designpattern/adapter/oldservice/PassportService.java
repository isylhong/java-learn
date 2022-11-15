package com.autumn.learn.java8.designpattern.adapter.oldservice;

/**
 *
 * @author yl
 * @since 2022-11-15 19:34
 */
public class PassportService {

    /**
     * class 03
     * 注册方法
     */
    public ResultMsg register(String username, String password) {
        return new ResultMsg(200, "注册成功", new Member());
    }

    /**
     * 登录方法
     */
    public ResultMsg login(String username, String password) {
        return new ResultMsg(200, "登录成功", null);
    }
}
