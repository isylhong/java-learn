package com.autumn.learn.java8.clazz.beaninfo;

import com.autumn.learn.java8.domain.employee.Employee;
import org.junit.Test;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @author yl
 * @since 2022-11-12 12:45
 */
public class BeanInfoTest {
    @Test
    public void testPropertyEditor() {
        Employee employee = new Employee(); // 初始化一个空实例(未给任何属性赋值)
        EmployeeBeanInfo employeeBeanInfo = new EmployeeBeanInfo(); // 创建Employee的自定义BeanInfo

        HashMap<String, String> map = new HashMap<>(); // 字符串形式的属性键值对
        map.put("employeeNumber", "222333");
        map.put("salary", "6000");
        map.put("department", "IT运维部,2");

        System.out.println("赋值前：" + employee);
        SimpleBeanWrapper beanWrapper = new SimpleBeanWrapper(employee);
        beanWrapper.setPropertyValue(employeeBeanInfo, map);
        System.out.println("赋值后：" + employee);
    }

    @Test
    public void testBeanInfo() {
        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(Employee.class);
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }

        assert beanInfo != null;
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor pd : propertyDescriptors) {
            String name = pd.getName();
            String displayName = pd.getDisplayName();
            String shortDescription = pd.getShortDescription();
            Method readMethod = pd.getReadMethod();
            Method writeMethod = pd.getWriteMethod();
            Class<?> propertyType = pd.getPropertyType();
            Class<?> propertyEditorClass = pd.getPropertyEditorClass();

            System.out.println("name: " + name);
            System.out.println("displayName: " + displayName);
            System.out.println("shortDescription: " + shortDescription);
            System.out.println("readMethod: " + readMethod);
            System.out.println("writeMethod: " + writeMethod);
            System.out.println("propertyType: " + propertyType);
            System.out.println("propertyEditorClass: " + propertyEditorClass);
            System.out.println();
        }
    }
}
