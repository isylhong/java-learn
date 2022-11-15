package com.autumn.learn.java8.designpattern.wrapper;

/**
 * TODO
 *
 * @author yl
 * @since 2022-11-15 19:57
 */
public abstract class AbstractSleep implements Sleep {
    protected Sleep sleep; // 注意此次访问修饰符为 protected
}