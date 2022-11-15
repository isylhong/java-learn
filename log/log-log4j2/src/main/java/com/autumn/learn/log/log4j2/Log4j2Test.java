package com.autumn.learn.log.log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

/**
 * 单独使用log4j2
 * 依赖包：log4j-api、log4j-core
 * 配置文件： log4j2.xml
 *
 */
public class Log4j2Test {

    @Test
    public void testLog4j2() {
        Logger logger = LogManager.getLogger(Log4j2Test.class);
        logger.info(logger.getClass().getName());
    }
}