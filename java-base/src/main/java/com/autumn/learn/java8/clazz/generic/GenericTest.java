package com.autumn.learn.java8.clazz.generic;

import com.autumn.learn.java8.domain.BaseDao;
import com.autumn.learn.java8.domain.User;
import com.autumn.learn.java8.domain.UserDaoImpl;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yl
 * @since 2022-11-12 13:22
 */
public class GenericTest {

    private final Logger LOGGER = LoggerFactory.getLogger(GenericTest.class);

    /**
     * 类BaseDao使用了泛型参数T。指定泛型参数类型，new一个BaseDao实例，无法通过 getGenericSuperclass() 获取到BaseDao中的泛型具体类型
     */
    @Test
    public void superClassGenericTypeGetTest02() {
        BaseDao<User> userBaseDao = new BaseDao<>();
        userBaseDao.init();
        String parameterizedTypeClassName = userBaseDao.getParameterizedTypeClassName(); // null
        LOGGER.info("指定泛型参数类型，直接new一个BaseDao实例，泛型具体类型为：{}", parameterizedTypeClassName);
    }

    /**
     * 定义子类BaseDaoImpl继承BaseDao，并在子类定义中指定泛型参数T的具体类型。可通过 getGenericSuperclass() 获取到BaseDao中的泛型具体类型。
     */
    @Test
    public void superClassGenericTypeGetTest() {
        UserDaoImpl userDao = new UserDaoImpl();
        userDao.initInSubClass();
        String parameterizedTypeClassName = userDao.getParameterizedTypeClassName(); // com.autumn.java8.User
        LOGGER.info("子类定义时指定父类泛型具体类型，new一个子类实例，泛型具体类型为：{}", parameterizedTypeClassName);
    }

    @Test
    public void superClassGenericTypeGetTest2() {
        UserDaoImpl userDao = new UserDaoImpl();
        userDao.init();
        String parameterizedTypeClassName = userDao.getParameterizedTypeClassName(); // com.autumn.java8.User
        LOGGER.info("子类定义时指定父类泛型具体类型，new一个子类实例，泛型具体类型为：{}", parameterizedTypeClassName);
    }
}
