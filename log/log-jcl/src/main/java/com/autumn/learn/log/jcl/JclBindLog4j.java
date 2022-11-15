package com.autumn.learn.log.jcl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 * jcl + log4j
 * 门面包：commons-logging
 * 实现包：log4j
 *
 *  1、在类路径中配置 log4j.xml/log4j.properties 配置文件。
 *  2、在类路径中配置 commons-logging.properties 配置文件，并添加如下内容:
 *     org.apache.commons.logging.Log=org.apache.commons.logging.impl.Log4JLogger
 */

public class JclBindLog4j {

    @Test
    public void testJclBindLog4j() {
        // org.apache.commons.logging.impl.Log4JLogger
        Log log = LogFactory.getLog(JclBindLog4j.class);
        log.info("log: " + log.getClass().getName());
    }
}
