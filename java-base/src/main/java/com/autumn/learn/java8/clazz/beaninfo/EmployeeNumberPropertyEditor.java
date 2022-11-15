package com.autumn.learn.java8.clazz.beaninfo;

import java.beans.PropertyEditorSupport;

/**
 * @author yl
 * @since 2022-11-12 13:09
 */
public class EmployeeNumberPropertyEditor extends PropertyEditorSupport {

    @Override
    public String getAsText() {
        return super.getAsText();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null || text.equals("")) {
            throw new IllegalArgumentException("设置的字符串格式不正确");
        }
        StringBuilder sb = new StringBuilder(text);
        int length = text.length();
        if (length < 8) {
            for (int i = 0; i < 8 - length; i++) {
                sb.insert(0, '0');
            }
        }
        super.setValue(sb.toString()); // 将不是8位数值的工号通过加数前加0补足8位保存
    }
}
