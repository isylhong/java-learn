package com.autumn.learn.java8.clazz.beaninfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author yl
 * @since 2022-11-12 13:11
 */
@NoArgsConstructor
@Getter
@Setter
public class SimpleBeanWrapper {
    private Object wrappedObject;
    private Class<?> rawType;

    public SimpleBeanWrapper(Object instance) {
        this.wrappedObject = instance;
        this.rawType = instance.getClass();
    }

    public void setPropertyValue(BeanInfo beanInfo, Map<String, String> values) {
        // 1、获取自定义的PropertyDescriptor
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        // 2、构建一个Map存储每一个属性相关信息
        HashMap<String, PropertyHolder> propertyHolders = new HashMap<>();
        for (PropertyDescriptor pd : propertyDescriptors) {
            propertyHolders.put(pd.getName(), createPropertyHolder(pd));
        }

        // 3、遍历属性字符串键值对，将字符串值转换为目标类型值，然后赋值给实例对象
        Set<Map.Entry<String, String>> entries = values.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            PropertyHolder propertyHolder = propertyHolders.get(entry.getKey());
            try {
                // 1. 通过自定义PropertyEditor将字符串值转换属性目标类型值
                propertyHolder.propertyEditor.setAsText(entry.getValue());
                // 2. 获取经自定义PropertyEditor转换后的属性值
                Object convertedValue = propertyHolder.propertyEditor.getValue();
                // 3. 通过反射将转换值赋值给实例对象
                propertyHolder.writeMethod.invoke(this.wrappedObject, convertedValue);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }


    public PropertyHolder createPropertyHolder(PropertyDescriptor pd) {
        return pd != null ? new PropertyHolder(pd) : null;
    }

    class PropertyHolder {
        private String name;
        private Class<?> type;
        private Method readMethod;
        private Method writeMethod;
        private PropertyDescriptor pd;
        private PropertyEditor propertyEditor;

        public PropertyHolder(PropertyDescriptor pd) {
            this.pd = pd;
            this.name = pd.getName();
            this.type = pd.getPropertyType();
            this.readMethod = pd.getReadMethod();
            this.writeMethod = pd.getWriteMethod();
            try {
                this.propertyEditor = (PropertyEditor) pd.getPropertyEditorClass().newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
