package com.autumn.learn.log.slf4j;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * slf4j + logback
 * 门面包：slf4j-api
 * 实现包：logback-core、logback-classic
 *
 * 配置文件： logback.xml
 *
 * logback在启动时，根据以下步骤寻找配置文件：
 *  1）在classpath中寻找logback-test.xml文件
 *  2）如果找不到logback-test.xml，则在 classpath中寻找logback.groovy文件
 *  3）如果找不到 logback.groovy，则在classpath中寻找logback.xml文件
 *  4）如果上述的文件都找不到，则logback会使用JDK的SPI机制查找 META-INF/services/ch.qos.logback.classic.spi.Configurator中的 logback 配置实现类，这个实现类必须实现Configuration接口，使用它的实现来进行配置
 *  5）如果上述操作都不成功，logback 就会使用它自带的 BasicConfigurator 来配置，并将日志输出到console。
 */
public class Slf4jBindLogback {

    @Test
    public void useSl4jAndLogback() {
        Logger logger = LoggerFactory.getLogger(Slf4jBindLogback.class);
        logger.info(logger.getClass().getName());
    }
}
