package com.autumn.learn.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 *
 * @author yl
 * @since 2022-11-14 23:13
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Department {
    private String name;
    private int level;
    private List<Employee> employees;
}
