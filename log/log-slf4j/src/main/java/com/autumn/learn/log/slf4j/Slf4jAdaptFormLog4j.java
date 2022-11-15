package com.autumn.learn.log.slf4j;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * slf4j + log4j
 * 门面包：slf4j-api
 * 实现包：log4j
 * 适配包：slf4j-log4j12
 *
 * 配置文件： log4j.properties
 */

public class Slf4jAdaptFormLog4j {

    @Test
    public void testSlf4jBindLog4j() {
        // logger: org.slf4j.impl.Log4jLoggerAdapter
        Logger logger = LoggerFactory.getLogger(Slf4jAdaptFormLog4j.class);
        logger.info(logger.getClass().getName());
    }

}
