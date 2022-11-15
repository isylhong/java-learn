package com.autumn.learn.java8.clazz.reflect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 *
 * @author yl
 * @since 2022-11-12 14:02
 */
public class SubReflection extends ParentReflection {
    public int pub;
    protected int[] pro;
    private String pri;

    private final Logger LOGGER = LoggerFactory.getLogger(SubReflection.class);

    public SubReflection() {
    }

    public SubReflection(int pub, int[] pro) {
        this.pub = pub;
        this.pro = pro;
    }

    public SubReflection(int pub, int[] pro, String pri) {
        this(pub, pro);
        this.pri = pri;
    }

    public int getPub() {
        return pub;
    }

    public void setPub(int pub) {
        this.pub = pub;
    }

    public int[] getPro() {
        return pro;
    }

    public void setPro(int[] pro) {
        this.pro = pro;
    }

    public String getPri() {
        return pri;
    }

    public void setPri(String pri) {
        this.pri = pri;
    }


    @Override
    public String toString() {
        return "MyReflection{" +
                "pub=" + pub +
                ", pro=" + Arrays.toString(pro) +
                ", pri='" + pri + "'}, " + super.toString();
    }

    public void publicMethodSub() {
        LOGGER.info("subclass public method");
    }

    protected void protectedMethodSub() {
        LOGGER.info("subclass protected method");
    }

    private void privateMethodSub() {
        LOGGER.info("subclass private method");
    }

    void packageMethodSub() {
        LOGGER.info("subclass package method");
    }
}
