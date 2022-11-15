package com.autumn.learn.java8.clazz.reflect;

import com.autumn.learn.java8.domain.phone.Phone;
import com.autumn.learn.java8.utils.ClassUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Method反射测试
 *
 * @author yl
 * @since 2022-11-12 13:56
 */
public class MethodReflectTest {
    private final Logger LOGGER = LoggerFactory.getLogger(MethodReflectTest.class);

    /**
     * 通过反射获取类中的指定方法，并执行
     */
    @Test
    public void methodInvokeTest() throws InstantiationException,
            IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class<SubReflection> myReflectionClass = SubReflection.class;

        SubReflection subReflection = myReflectionClass.newInstance();

        // 反射调用public方法
        Method publicMethod = myReflectionClass.getDeclaredMethod("publicMethodSub");
        publicMethod.invoke(subReflection);

        // 反射调用protected方法, 需使用getDeclaredMethod(...)方法; 使用getMethod(...)方法会报异常 java.lang.NoSuchMethodException
        Method protectedMethod = myReflectionClass.getDeclaredMethod("protectedMethodSub");
        protectedMethod.setAccessible(true); // protected修饰的方法需设置访问权限
        protectedMethod.invoke(subReflection);

        // 反射调用private方法, 需使用getDeclaredMethod(...)方法; 使用getMethod(...)方法会报异常 java.lang.NoSuchMethodException
        Method privateMethod = myReflectionClass.getDeclaredMethod("privateMethodSub");
        privateMethod.setAccessible(true); // private修饰的方法需设置访问权限
        privateMethod.invoke(subReflection);

    }

    /**
     * 反射获取Method对象
     * getDeclaredMethods()：获取指定类(不含祖父类)、接口类中所有 private、protected、public、default 修饰的方法，包括当前类中重载的的祖父类方法。
     * getMethods()：获取到指定类(含祖父类)、接口类中所有 public、default 修饰的方法。如果子类中有重载方法，则获取子类中重载的方法。
     */
    @Test
    public void classMethodsGetTest() {
        Class<SubReflection> myReflectionClass = SubReflection.class;

        // getMethods()：获取当前类(含祖父类)中的所有public、default修饰方法
        LOGGER.info("getMethods()：获取当前类(含祖父类)中的所有public、default修饰方法");
        Method[] methods = myReflectionClass.getMethods();
        for (Method method : methods) {
            // 打印非Object类中的方法，同时不打印setXXX()和getXXX()方法
            if (!method.getDeclaringClass().getSimpleName().equals("Object")) {
                String methodName = method.getName();
                if (methodName.startsWith("set") || methodName.startsWith("get")) {
                    continue;
                }
                String modifierStr = ClassUtil.getModifierStr(method.getModifiers()); // 获取方法修饰符
                Class<?>[] methodParameterTypes = method.getParameterTypes(); // 获取方法参数类型
                LOGGER.info("{}", method);
            }
        }

        // getDeclaredMethods()：获取当前类(不包括祖父类)、接口类中的所有private、protected、public、package、default修饰的方法, 包括当前类中重载的祖父类方法
        System.out.println();
        LOGGER.info("getDeclaredMethods()：获取当前类(不包括祖父类)、接口类中的所有private、protected、public、package、default修饰的方法, " +
                "包括当前类中重载的祖父类方法");
        Method[] declaredMethods = myReflectionClass.getDeclaredMethods();
        for (Method method : declaredMethods) {
            String methodName = method.getName();
            if (methodName.startsWith("set") || methodName.startsWith("get")) {
                continue;
            }
            LOGGER.info("{}", method);
        }
    }

    @Test
    public void interfaceMethodsGetTest() {
        Class<Phone> fruitClass = Phone.class;

        // getMethods()获取当前类及父类（含祖父类）中的所有public方法
        LOGGER.info("getMethods()获取当前类及父类（含祖父类）中的所有public方法");
        Method[] methods = fruitClass.getMethods();
        for (Method method : methods) {
            // 打印非Object类中的方法，同时不打印setXXX()和getXXX()方法
            if (!method.getDeclaringClass().getSimpleName().equals("Object")) {
                String methodName = method.getName();
                if (methodName.startsWith("set") || methodName.startsWith("get")) {
                    continue;
                }
                String modifierStr = ClassUtil.getModifierStr(method.getModifiers()); // 获取方法修饰符
                Class<?>[] methodParameterTypes = method.getParameterTypes(); // 获取方法参数类型
                LOGGER.info("{}", method);
            }
        }

        // getDeclaredMethods()获取所有方法(含private、protected、public、package), 不包括祖父类中的方法
        System.out.println();
        LOGGER.info("getDeclaredMethods()获取当前类中的所有方法(含private、protected、public、package), 不包括父类中的方法");
        Method[] declaredMethods = fruitClass.getDeclaredMethods();
        for (Method method : declaredMethods) {
            String methodName = method.getName();
            if (methodName.startsWith("set") || methodName.startsWith("get")) {
                continue;
            }
            LOGGER.info("{}", method);
        }
    }
}
