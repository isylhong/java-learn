package com.autumn.learn.java8.designpattern.adapter.oldservice;

import lombok.Data;

/**
 * 遵循开闭原则，老代码我们不修改。开启代码重构之路，创建Member类
 *
 * @author yl
 * @since 2022-11-15 19:30
 */
@Data
public class Member {
    private String username;
    private String password;
    private String mid;
    private String info;
}
