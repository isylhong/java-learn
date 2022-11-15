package com.autumn.learn.java8.utils;

/**
 *
 * @author yl
 * @since 2022-11-12 14:03
 */
public class NumberUtil {
    /**
     * 给定一个int数，返回指定位数二进制字符串
     */
    public static String toBinaryString(int num, int offset) {
        StringBuilder s = new StringBuilder(Integer.toBinaryString(num));
        for (int i = s.length(); i < offset; i++) {
            s.insert(0, "0"); // 不足32位，填0
        }
        for (int i = 1; i < (offset / 8); i++) {
            s.insert(i * 8 + i - 1, " "); // 每8位用空格隔开
        }
        return s.toString();
    }

    /**
     * 给定一个int数，返回对应的32位二进制字符串
     */
    public static String toBinaryString32(int num) {
        StringBuilder s = new StringBuilder(Integer.toBinaryString(num));
        for (int i = s.length(); i < 32; i++) {
            s.insert(0, "0"); // 不足32位，填0
        }
        for (int i = 1; i < 4; i++) {
            s.insert(i * 8 + i - 1, " "); // 每8位用空格隔开
        }
        return s.toString();
    }
}
