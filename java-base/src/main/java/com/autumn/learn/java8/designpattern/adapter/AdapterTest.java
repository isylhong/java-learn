package com.autumn.learn.java8.designpattern.adapter;

import com.autumn.learn.java8.designpattern.adapter.oldservice.ResultMsg;
import com.autumn.learn.java8.designpattern.adapter.refactor.IPassportForThird;
import com.autumn.learn.java8.designpattern.adapter.refactor.PassportForThirdAdapter;
import org.junit.Test;

/**
 * 适配器模式
 *
 * @author yl
 * @since 2022-11-15 19:30
 */
public class AdapterTest {
    @Test
    public void testAdapter() {
        IPassportForThird adapter = new PassportForThirdAdapter();
        ResultMsg resultMsg = adapter.loginForQQ("666666");
        System.out.println(resultMsg);
    }
}
