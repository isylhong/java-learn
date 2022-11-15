package com.autumn.learn.java8.domain.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author yl
 * @since 2022-11-12 12:46
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Department {
    private String name;
    private byte level; // 部门层级
}
