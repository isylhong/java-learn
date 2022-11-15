package com.autumn.learn.java8.domain.phone;

/**
 * @author yl
 * @since 2022-11-12 13:31
 */
//@CustomDescription("手机分类")
public interface Category {
    PhoneType getCategory();

    enum PhoneType {
        OLD, // /老人机
        SMART // 智能机
    }
}
