package com.autumn.learn.java8.designpattern.adapter.refactor;

import com.autumn.learn.java8.designpattern.adapter.oldservice.ResultMsg;

/**
 * 根据不同登录方式，创建不同登录Adaptor。首先，创建LoginAdapter接口
 *
 * @author yl
 * @since 2022-11-15 19:35
 */
public interface ILoginAdapter {
    boolean support(Object object);

    ResultMsg login(String id, Object adapter);
}
