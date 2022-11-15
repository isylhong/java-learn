package com.autumn.learn.java8.designpattern.adapter.refactor;

import com.autumn.learn.java8.designpattern.adapter.oldservice.ResultMsg;

/**
 * Token登录
 *
 * @author yl
 * @since 2022-11-15 19:39
 */
public class LoginForTokenAdapter extends AbstractAdapter {
    @Override
    public boolean support(Object adapter) {
        return adapter instanceof LoginForTokenAdapter;
    }

    @Override
    public ResultMsg login(String id, Object adapter) {
        return super.login(id, null);
    }
}
