package com.autumn.learn.java8.designpattern.adapter.oldservice;

import lombok.Data;

/**
 * 统一登陆结果ResultMsg类
 *
 * @author yl
 * @since 2022-11-15 19:34
 */
@Data
public class ResultMsg {
    private int code;
    private String msg;
    private Object data;

    public ResultMsg(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
