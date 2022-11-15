package com.autumn.learn.java8.encode;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnicodeTest {
    private static final Logger logger = LoggerFactory.getLogger(UnicodeTest.class);

    public static void main(String[] args) {
        System.out.println("---" + "\u0031" + "---");
        byte[] bytes = new byte[]{1, 0, 65, 97};
        String str = new String(bytes);
        logger.info(str);

        byte b = 65;
        logger.info(Integer.toHexString(18));

        int i = b & 0xff;
        logger.info(Integer.toBinaryString(i));
    }
}
