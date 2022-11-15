package com.autumn.learn.mybatis.dao;

import com.autumn.learn.mybatis.domain.Employee;

public interface EmployeeMapper {

    public Employee findEmployeeById(Integer id);

}
