package com.autumn.learn.java8.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * @author yl
 * @since 2022-11-12 13:20
 */
public class UserDaoImpl extends BaseDao<User> {
    private final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    public UserDaoImpl() {
    }

    public void initInSubClass() {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            LOGGER.info("ParameterizedType => {}", parameterizedType);
            LOGGER.info("ParameterizedType.getRawType() => {}", parameterizedType.getRawType());
            LOGGER.info("ParameterizedType.getActualTypeArguments() => {}",
                    Arrays.asList(parameterizedType.getActualTypeArguments()));
            Type[] types = parameterizedType.getActualTypeArguments();
            super.setParameterizedTypeClassName(types[0].getTypeName());
        }
    }
}
