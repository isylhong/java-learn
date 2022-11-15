package com.autumn.learn.mybatis.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author yl
 * @since 2022-11-14 22:18
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Order {
    private Integer id;
    private String orderNumber;
    private double total;
    private String userNumber;
}
