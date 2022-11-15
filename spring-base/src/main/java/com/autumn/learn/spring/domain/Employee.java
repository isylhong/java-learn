package com.autumn.learn.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
public class Employee {
    private String employeeName;
    private Department department;

    public Employee(Department department) {
        this.department = department;
    }
}
