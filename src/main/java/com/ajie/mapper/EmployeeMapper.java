package com.ajie.mapper;

import com.ajie.entity.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author:hyj
 * @date:2022/10/1
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
