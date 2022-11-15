package com.autumn.learn.java8.clazz.beaninfo;

import java.beans.PropertyEditorSupport;

/**
 * @author yl
 * @since 2022-11-12 13:09
 */
public class EmployeeSalaryPropertyEditor extends PropertyEditorSupport {

    @Override
    public String getAsText() {
        return super.getAsText();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null || text.equals("")) {
            throw new IllegalArgumentException("设置的字符串格式不正确");
        }
        super.setValue(Double.parseDouble(text));
    }
}
