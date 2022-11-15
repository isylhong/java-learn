package com.autumn.learn.java8.designpattern.adapter.refactor;

import com.autumn.learn.java8.designpattern.adapter.oldservice.ResultMsg;

/**
 * 运行稳定的代码不改动。创建ITarget角色IPassportForThird接口
 *
 * @author yl
 * @since 2022-11-15 19:35
 */
public interface IPassportForThird {
    public ResultMsg loginForQQ(String openId);

    public ResultMsg loginForWechat(String openId);

    public ResultMsg loginForToken(String token);

    public ResultMsg loginForTelephone(String phone, String code);
}
