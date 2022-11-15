package com.autumn.learn.java8.clazz.beaninfo;

import com.autumn.learn.java8.domain.employee.Department;

import java.beans.PropertyEditorSupport;

/**
 * @author yl
 * @since 2022-11-12 13:10
 */
public class EmployeeDepartmentPropertyEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if(text == null || !text.contains(",")){
            throw new IllegalArgumentException("设置的字符串格式不正确");
        }
        String[] infos = text.split(",");
        Department department = new Department();
        department.setName(infos[0]);
        department.setLevel(Byte.parseByte(infos[1]));

        //2. 调用父类的setValue()方法设置转换后的属性对象
        setValue(department);
    }
}
