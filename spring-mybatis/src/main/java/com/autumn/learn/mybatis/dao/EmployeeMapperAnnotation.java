package com.autumn.learn.mybatis.dao;

import com.autumn.learn.mybatis.domain.Employee;
import org.apache.ibatis.annotations.Select;


public interface EmployeeMapperAnnotation {
	
	@Select("select * from tb_employee where id=#{id}")
	public Employee findEmployeeById(Integer id);
}
