package com.autumn.learn.java8.clazz.reflect;

import com.autumn.learn.java8.utils.ClassUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * Constructor反射测试
 *
 * @author yl
 * @since 2022-11-12 14:00
 */
public class ConstructorReflectTest {
    private final Logger LOGGER = LoggerFactory.getLogger(FieldReflectTest.class);
    private final Class<SubReflection> myReflectionClass = SubReflection.class;

    /**
     * 通过反射实例化一个对象
     * 步骤：
     * 1. 反射获取Constructor对象，
     * 2. 通过Constructor对象的newInstance([...])方法实例化一个对象
     */
    @Test
    public void testInstantiateByReflect() throws NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {
        Class<SubReflection> myReflectionClass = SubReflection.class;

        // 无参构造方法创建实例
        Constructor<SubReflection> reflectionConstructor1 = myReflectionClass.getConstructor();
        SubReflection subReflection1 = reflectionConstructor1.newInstance();
        subReflection1.setPub(999);
        subReflection1.setPro(new int[]{1, 2});
        LOGGER.info(subReflection1.toString());

        // 有参构造方法创建实例
        Constructor<SubReflection> reflectionConstructor2 = myReflectionClass.getConstructor(int.class, int[].class,
                String.class);
        SubReflection subReflection2 = reflectionConstructor2.newInstance(999, new int[]{3, 4}, "private filed");
        LOGGER.info(subReflection2.toString());
    }

    /**
     * 通过反射获取构造函数
     */
    @Test
    public void testMethod_getConstructors() {
        // getConstructors()获取所有构造函数, 不包括父类构造函数
        LOGGER.info("getConstructors()获取所有构造函数, 不包括父类构造函数");
        Constructor[] constructors = myReflectionClass.getConstructors();
        for (Constructor constructor : constructors) {
            Class[] constructorParameterTypes = constructor.getParameterTypes();
            LOGGER.info("{} ({}), {}", constructor.getName(), ClassUtil.getModifierStr(constructor.getModifiers()),
                    Arrays.asList(constructorParameterTypes));
        }

        // getDeclaredConstructors()获取所有构造函数, 不包括父类构造函数
        LOGGER.info("getDeclaredConstructors()获取所有构造函数, 不包括父类构造函数");
        Constructor[] declaredConstructors = myReflectionClass.getDeclaredConstructors();
        for (Constructor constructor : declaredConstructors) {
            Class[] constructorParameterTypes = constructor.getParameterTypes();
            LOGGER.info("{} ({}), {}", constructor.getName(), ClassUtil.getModifierStr(constructor.getModifiers()),
                    Arrays.asList(constructorParameterTypes));
        }
    }
}
