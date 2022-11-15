package com.autumn.learn.log.log4j;

import org.junit.Test;

/**
 * log4j + slf4j
 * 依赖包：slf4j-api
 * 桥接包：log4j-over-slf4j (通过重写log4j包中的类实现)
 *
 * 注意事项：
 * 如果使用了log4j-over-slf4j，那么依赖中最好不要添加log4j这个包，
 * 假如非要在依赖中添加log4j这个依赖包，那么要保证log4j依赖配置放在log4j-over-slf4j依赖包后面，只有这样才能保证log4j桥接到slf4j；否则，桥接会失效。
 *
 */
public class Log4jBridgeToSlf4j {

    @Test
    public void testLog4jBridgeToSlf4j() {
        org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Log4jBridgeToSlf4j.class);
        logger.info(logger.getClass().getName());
    }
}
