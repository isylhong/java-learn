package com.autumn.learn.java8.domain.phone;

import java.lang.reflect.Field;

/**
 * 1、@CustomAttribute与@CustomAttributes
 * 定义：注解@CustomAttribute上标注了元注解@Repeatable，@CustomAttribute的注解容器为@CustomAttributes；
 * 使用单个@CustomAttribute注解时：getAnnotation()、getAnnotations()方法获取到的是@CustomAttribute。
 * 同一个类上同时使用多个@CustomAttribute注解时：getAnnotation()、getAnnotations()获取到的是@CustomAttribute的注解容器@CustomAttributes。
 *
 * 2、@CustomDescription：
 * 注解@CustomDescription上标注了元注解@Inherited，未标注@CustomRepeatable；
 * 子类上使用@CustomDescription注解，子类上的属性会替换父类中@CustomDescription注解的所有属性值（覆盖/删除）
 *
 * @author yl
 * @since 2022-11-12 12:38
 */
//@CustomDescription("苹果手机") // 父类和子类中存在相同注解时@CustomDescription,获取到的是子类上的注解@CustomDescription
//@CustomAttribute("粉红色")
//@CustomAttribute("190g") // 注意：getAnnotation(CustomAttribute.class)获取到的是CustomAttributes注解
public class iPhone extends AbstractPhone implements Phone, Category {

    @Price(value = "5999")
    private int price;

    public iPhone() {
        super("1001", "iphone 4");
    }

    @Override
    public void func() {
        System.out.println("打电话");
        System.out.println("发短信");
    }

    public int getPrice() {
        Field priceField = null;
        try {
            priceField = this.getClass().getDeclaredField("price");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        assert priceField != null;
        Price annotation = priceField.getAnnotation(Price.class);
        return Integer.parseInt(annotation.value());
    }

    @Override
    public PhoneType getCategory() {
        return PhoneType.SMART;
    }

    @Override
    public String getSize() {
        return "6.1寸";
    }
}
