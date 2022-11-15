package com.autumn.learn.java8.clazz.beaninfo;

import com.autumn.learn.java8.domain.employee.Employee;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

/**
 * @author yl
 * @since 2022-11-12 13:10
 */
public class EmployeeBeanInfo extends SimpleBeanInfo {

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            //1. 将EmployeeNumberPropertyEditor绑定到Employee的employeeNumber属性中
            PropertyDescriptor idDescriptor = new PropertyDescriptor("employeeNumber", Employee.class);
            idDescriptor.setPropertyEditorClass(EmployeeNumberPropertyEditor.class);
            //2. 将EmployeeSalaryPropertyEditor绑定到Employee的salary属性中
            PropertyDescriptor salaryDescriptor = new PropertyDescriptor("salary", Employee.class);
            salaryDescriptor.setPropertyEditorClass(EmployeeSalaryPropertyEditor.class);
            //3. 将EmployeeDepartmentPropertyEditor绑定到Employee的department属性中
            PropertyDescriptor departmentDescriptor = new PropertyDescriptor("department", Employee.class);
            departmentDescriptor.setPropertyEditorClass(EmployeeDepartmentPropertyEditor.class);

            return new PropertyDescriptor[]{idDescriptor, salaryDescriptor, departmentDescriptor};
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
