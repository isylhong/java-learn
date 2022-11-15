package com.autumn.learn.log.slf4j;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * slf4j + log4j2
 * 门面包：slf4j-api
 * 实现包：log4j-api、log4j-core
 * 适配包：log4j-slf4j-impl
 *
 * 配置文件： log4j2.xml/log4j2.properties
 */
public class Slf4jBindLog4j2 {

    @Test
    public void useSl4jAndLog4j2() {
        Logger logger = LoggerFactory.getLogger(Slf4jBindLog4j2.class);
        logger.info(logger.getClass().getName());
    }
}
