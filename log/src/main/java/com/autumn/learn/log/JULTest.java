package com.autumn.learn.log;

import org.junit.Test;

import java.util.logging.Logger;

/**
 * 直接使用jul，JDK自带，无需引入其他jar包
 */
public class JULTest {

    @Test
    public void testJul() {
        Logger logger = Logger.getLogger(JULTest.class.getName());
        logger.info(logger.getClass().getName());
    }

}
