package com.autumn.learn.java8.clazz.reflect;

import com.autumn.learn.java8.utils.ClassUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Filed反射测试
 *
 * @author yl
 * @since 2022-11-12 13:58
 */
public class FieldReflectTest {
    private final Logger LOGGER = LoggerFactory.getLogger(FieldReflectTest.class);

    private final Class<SubReflection> myReflectionClass = SubReflection.class;

    /**
     * 反射获取字段
     * 注意：
     * getDeclaredFields()方法可获取到指定类中 private、protected、public 修饰的所有字段
     * getFields()方法只能获取到指定类（含祖父类）中 public 修饰的所有字段
     */
    @Test
    public void fieldsGetTest() {
        // getFields()获取所有public字段, 包括父类（含祖父类）中的所有public字段
        LOGGER.info("getFields()获取所有public字段, 包括父类（含祖父类）中的所有public字段");
        Field[] fields = myReflectionClass.getFields();
        for (Field field : fields) {
            int modifiers = field.getModifiers();
            LOGGER.info("{} ({}) ", field.getName(), ClassUtil.getModifierStr(modifiers));
        }

        // getDeclaredFields()获取所有字段(含private、protected、public), 不包括父类/祖父类中的字段
        // 获取所有字段(public, protected, private)，不包括父类字段
        LOGGER.info("获取所有字段(public, protected, private)，不包括父类字段");
        Field[] declaredFields = myReflectionClass.getDeclaredFields();
        for (Field field : declaredFields) {
            int modifiers = field.getModifiers();
            LOGGER.info("{} ({}) ", field.getName(), ClassUtil.getModifierStr(modifiers));
        }
    }

    /**
     * 反射访问指定类中的属性
     */
    @Test
    public void filedAccessTest() throws NoSuchFieldException, InstantiationException, IllegalAccessException {

        SubReflection subReflection = myReflectionClass.newInstance();

        // 反射访问public字段
        Field publicField = myReflectionClass.getField("pub");
        publicField.set(subReflection, 999);

        // 反射访问protected字段, 需使用getDeclaredField(...)方法; 使用getField(...)方法会报异常java.lang.NoSuchFieldException
        Field protectedField = myReflectionClass.getDeclaredField("pro");
        protectedField.setAccessible(true); // protected修饰的方法需设置访问权限
        protectedField.set(subReflection, new int[]{1, 2});

        // 反射访问private字段, 需使用getDeclaredField(...)方法; 使用getField(...)方法会报异常java.lang.NoSuchFieldException
        Field privateField = myReflectionClass.getDeclaredField("pri");
        privateField.setAccessible(true); // private修饰的方法需设置访问权限
        privateField.set(subReflection, "a private field");

        LOGGER.info(subReflection.toString());
    }

    /**
     * 通过反射访问父类（含祖父类）中字段
     */
    @Test
    public void superClassFiledAccessTest() throws InstantiationException, IllegalAccessException
            , NoSuchFieldException {

        SubReflection subReflection = myReflectionClass.newInstance();

        // 1. 访问父类public字段。
        Field pubFiled = myReflectionClass.getField("p_pub");
        pubFiled.set(subReflection, "super public field...");


        // 2. 访问父类（含祖父类）的所有protected字段。
        // 子类无法通过getDeclaredField(...)直接获取到父类中的字段。
        // 方法：递归父类。先通过getSuperclass()获取父类，再通过父类中的getDeclaredField(...)方法访问父类中的protected方法
        Class<? super SubReflection> superClass = myReflectionClass.getSuperclass();
        while (superClass != null) {
            Field proFiled;
            try {
                proFiled = superClass.getDeclaredField("p_pro");
            } catch (NoSuchFieldException e) {
                superClass = superClass.getSuperclass();
                continue;
            }
            proFiled.setAccessible(true);
            proFiled.set(subReflection, "super protected field...");
            superClass = superClass.getSuperclass();
        }


        // 3. 访问父类（含祖父类）的所有private字段
        // 遍历父类，通过父类的getDeclaredFields()获取父类所有字段，遍历字段查找符合要求的字段
        List<Field> fieldList;
        fieldList = new ArrayList<>();
        superClass = myReflectionClass.getSuperclass();
        while (superClass != null) { // 逐级遍历父类
            fieldList.addAll(Arrays.asList(superClass.getDeclaredFields()));
            superClass = superClass.getSuperclass();
        }
        Field priFiledSuper = null;
        for (Field field : fieldList) {
            if (field.getName().equals("p_pri")) {
                priFiledSuper = field;
                priFiledSuper.setAccessible(true);
                priFiledSuper.set(subReflection, "super private field...");
            }
        }
        LOGGER.info(subReflection.toString());
    }
}
