package com.autumn.learn.log.log4j;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * 单独使用log4j
 * 依赖包：log4j
 * 配置文件： log4j.properties/log4j.xml
 *
 */
public class Log4jTest {

    @Test
    public void testLog4j() {
        Logger logger = Logger.getLogger(Log4jTest.class.getName());
        logger.info(logger.getClass().getName());
    }
}
