package com.autumn.learn.log.slf4j;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * slf4j + jcl
 * 门面包：slf4j-api、commons-logging
 * 桥接包：slf4j-jcl
 *
 * 配置文件： logback.xml
 */
public class Slf4jBridgeToJcl {

    @Test
    public void test(){
        // org.slf4j.impl.JCLLoggerAdapter
        Logger logger = LoggerFactory.getLogger(Slf4jBridgeToJcl.class);
        logger.info(logger.getClass().getName());
    }
}
