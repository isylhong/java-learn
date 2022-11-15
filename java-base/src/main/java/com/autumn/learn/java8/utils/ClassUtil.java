package com.autumn.learn.java8.utils;

import java.lang.reflect.Modifier;

/**
 *
 * @author yl
 * @since 2022-11-12 14:02
 */
public class ClassUtil {
    public static String getModifierStr(int modifier) {
        if (Modifier.isPrivate(modifier)) {
            return "private";
        } else if (Modifier.isProtected(modifier)) {
            return "protected";
        } else if (Modifier.isPublic(modifier)) {
            return "public";
        } else {
            return "";
        }
    }
}
