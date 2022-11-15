package com.autumn.learn.spring.aop;

import org.springframework.stereotype.Service;

/**
 *
 * @author yl
 * @since 2022-11-14 23:02
 */
@Service("myService")
public class MyService {
    public boolean save(String arg) {
        System.out.println("do insert " + arg);
        return true;
    }
}
