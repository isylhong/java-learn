package com.autumn.learn.log.jcl;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * jcl + slf4j
 * 门面包：commons-logging、slf4j-api
 * 实现包：log4j
 * 桥接包：jcl-over-slf4j
 *
 */
public class JclBridgeToSlf4j {

    @Test
    public void testJclBridgeToSlf4j() {

        Logger logger = LoggerFactory.getLogger(JclBridgeToSlf4j.class);
        logger.info(logger.getClass().getName());
    }
}
