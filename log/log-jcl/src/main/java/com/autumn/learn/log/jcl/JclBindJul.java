package com.autumn.learn.log.jcl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 * cjl + jul
 * 门面包：commons-logging
 * 实现包：JDK自带
 */

public class JclBindJul {

    @Test
    public void testJclBindJul() {
        // org.apache.commons.logging.impl.Jdk14Logger
        Log log = LogFactory.getLog(JclBindJul.class);
        log.info(log.getClass().getName());
    }

}
