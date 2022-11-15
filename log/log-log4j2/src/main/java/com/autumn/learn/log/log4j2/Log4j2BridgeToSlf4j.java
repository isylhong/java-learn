package com.autumn.learn.log.log4j2;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

/**
 * log4j2 + slf4j
 * 依赖包：log4j-api、log4j-core、slf4j-api
 * 桥接包：log4j-to-slf4j (通过重写log4j包中的类实现)
 */
public class Log4j2BridgeToSlf4j {
    private final Logger LOGGER = LogManager.getLogger(Log4j2BridgeToSlf4j.class);

    @Test
    public void testLog4j2BridgeToSlf4j() {
        LOGGER.info(LOGGER.getClass().getName());
    }

}
