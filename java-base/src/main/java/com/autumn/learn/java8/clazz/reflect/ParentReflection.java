package com.autumn.learn.java8.clazz.reflect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author yl
 * @since 2022-11-12 14:01
 */
public class ParentReflection {
    public String p_pub;
    protected String p_pro;
    private String p_pri;

    private final Logger LOGGER = LoggerFactory.getLogger(ParentReflection.class);

    public ParentReflection() {
    }

    public ParentReflection(String p_pub, String p_pro, String p_pri) {
        this.p_pub = p_pub;
        this.p_pro = p_pro;
        this.p_pri = p_pri;
    }

    public String getP_pub() {
        return p_pub;
    }

    public void setP_pub(String p_pub) {
        this.p_pub = p_pub;
    }

    public String getP_pro() {
        return p_pro;
    }

    public void setP_pro(String p_pro) {
        this.p_pro = p_pro;
    }

    public String getP_pri() {
        return p_pri;
    }

    public void setP_pri(String p_pri) {
        this.p_pri = p_pri;
    }

    @Override
    public String toString() {
        return "ParentReflection{" +
                "p_pub='" + p_pub + '\'' +
                ", p_pro='" + p_pro + '\'' +
                ", p_pri='" + p_pri + '\'' +
                '}';
    }


    public void publicMethod() {
        LOGGER.info("super class public method");
    }

    protected void protectedMethod() {
        LOGGER.info("super class protected method");
    }

    private void privateMethod() {
        LOGGER.info("super class private method");
    }

    void packageMethod() {
        LOGGER.info("super class package method");
    }
}
