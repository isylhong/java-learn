package com.autumn.learn.java8.clazz;

import com.autumn.learn.java8.domain.User;
import com.autumn.learn.java8.domain.UserDaoImpl;
import com.autumn.learn.java8.domain.phone.AbstractPhone;
import com.autumn.learn.java8.domain.phone.Phone;
import com.autumn.learn.java8.domain.phone.iPhone;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * Class类对象方法
 *
 * @author yl
 * @since 2022-11-12 13:16
 */
public class ClassTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassTest.class);

    /**
     * getDeclaringClass()：获取最后实现该方法的类对象
     * 1) 父类方法在子类中被重写/实现，获取到是的子类Class对象
     * 2) 父类方法没有在子类中被重载，获取到是的父类
     */
    @Test
    public void testMethod_getDeclaringClass() throws NoSuchMethodException {
        Method getSizeMethod = iPhone.class.getMethod("getSize");  // 父类方法，在子类中重载
        System.out.println(getSizeMethod.getDeclaringClass()); // com.autumn.learn.java8.domain.fruit.iPhone4

        Method getSizeMethod1 = AbstractPhone.class.getMethod("getSize");
        System.out.println(getSizeMethod1.getDeclaringClass());

        Method getIdMethod = iPhone.class.getMethod("getId"); // 父类方法，未在子类中重载
        System.out.println(getIdMethod.getDeclaringClass()); // com.autumn.learn.java8.domain.fruit.AbstractPhone
    }

    /**
     * getInterfaces()
     * 只能获取当前类实现的所有接口
     * 1. 不能获取父类(祖父)中实现接口
     * 2. 不能获取当前类实现接口继承的接口
     * 3. XXX接口.getInterfaces()用于获取该接口继承的接口列表
     */
    @Test
    public void testMethod_getInterfaces() {

        LOGGER.info(Arrays.asList(iPhone.class.getInterfaces()).toString());

        LOGGER.info("");
        LOGGER.info(Arrays.asList(AbstractPhone.class.getInterfaces()).toString());

        LOGGER.info("");
        LOGGER.info(Arrays.asList(Phone.class.getInterfaces()).toString()); // 获取该接口继承所有的接口列表
    }

    /**
     * getSuperclass()、getGenericSuperclass() 的区别
     * 同：两者都用于获取父类（使用extends关键字继承的类）；一个具体类(或抽象类)未使用extends继承其他类时，获取的父类是Object.class。
     * 异：getSuperclass()无法获取到父类中泛型的具体类型，而getGenericSuperclass()却可以获取到父类中泛型的具体类型。
     * <p>
     * 注意：
     * 1) Object.class.getSuperclass()=null。
     * 2) 无论接口有没有继承其他接口。即，接口.class.getSuperclass()=null。
     * 3) 可通过getInterfaces()获取接口继承的接口。
     */
    @Test
    public void testMethod_getSuperClass() {
        Class<UserDaoImpl> userDaoImplClass = UserDaoImpl.class;

        Class<? super UserDaoImpl> superclass = userDaoImplClass.getSuperclass();
        LOGGER.info("getSuperclass(): {}", superclass);

        Type genericSuperclass = userDaoImplClass.getGenericSuperclass();
        LOGGER.info("getGenericSuperclass(): {}", genericSuperclass);

        // 获取泛型具体类型
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        LOGGER.info("泛型的具体类型: {}", actualTypeArguments[0].getTypeName());
    }

    /**
     * instanceof、isAssignableFrom() 的区别
     * 1、变量A instanceof 类型B, 假设变量A所指的具体对象类型是C.
     * true: B是C的父类(或祖父类)，或B.class==C.class,
     * 2、A.getClass().isAssignableFrom(B.class)
     * true: A.getClass()是B.class父类(或祖父类)，或A.class==B.class.
     */
    @Test
    public void testMethod_isAssignableFrom() {
        Phone phone = new AbstractPhone() {
            @Override
            public void func() {
            }
        };

        // 变量var instanceof 类型B
        LOGGER.info("instanceof");
        LOGGER.info(String.valueOf(phone instanceof Phone)); // true
        LOGGER.info("{}", phone instanceof AbstractPhone); // true
        LOGGER.info("{}", phone instanceof iPhone); // false

        // xxxClass.isAssignableFrom(yyyClass)
        LOGGER.info("isAssignableFrom()");
        Class<AbstractPhone> abstractPhoneClass = AbstractPhone.class;
        LOGGER.info("{}", abstractPhoneClass.isAssignableFrom(Phone.class)); // false
        LOGGER.info("{}", abstractPhoneClass.isAssignableFrom(AbstractPhone.class)); // true
        LOGGER.info("{}", abstractPhoneClass.isAssignableFrom(iPhone.class)); // true
    }

    /**
     * 不同方法获取class name的区别
     */
    @Test
    public void testGetClassname() {
        Class<User> userClass = User.class;
        String classTypeName = userClass.getTypeName(); // com.autumn.learn.java8.domain.User
        LOGGER.info("type name: {}", classTypeName);
        String className = userClass.getName(); // com.autumn.learn.java8.domain.User
        LOGGER.info("name: {}", className);
        String classSimpleName = userClass.getSimpleName(); // User
        LOGGER.info("simple name: {}", classSimpleName);
        String classCanonicalName = userClass.getCanonicalName(); // com.autumn.learn.java8.domain.User
        LOGGER.info("canonical name: {}", classCanonicalName);
    }

    /**
     * 三种方法获取Class对象
     */
    @Test
    public void testGetClassObject() throws ClassNotFoundException {
        // 方法一：xxxClass.class
        Class<User> userClass1 = User.class;
        LOGGER.info(userClass1.getName());

        // 方法二：xxxObject.getClass()
        User user = new User();
        Class userClass2 = user.getClass();
        LOGGER.info(userClass2.getName());

        // 方法三：Class.forName(XXX类全限定名)
        Class UserClass3 = Class.forName("com.autumn.learn.java8.domain.User");
        LOGGER.info(UserClass3.getName());
    }
}
