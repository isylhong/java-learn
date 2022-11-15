package com.autumn.learn.java8.designpattern.adapter.refactor;

import com.autumn.learn.java8.designpattern.adapter.oldservice.ResultMsg;

/**
 * QQ登录
 *
 * @author yl
 * @since 2022-11-15 19:38
 */
public class LoginForQQAdapter extends AbstractAdapter {
    @Override
    public boolean support(Object adapter) {
        return adapter instanceof LoginForQQAdapter;
    }

    @Override
    public ResultMsg login(String id, Object adapter) {
        if (!support(adapter)) {
            return null;
        }
        //accesseToken
        //time
        return super.login(id, null);
    }
}
