package com.autumn.learn.java8.domain.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author yl
 * @since 2022-11-12 13:07
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Employee {
    private String employeeNumber;
    private double salary;
    private Department department;
}
