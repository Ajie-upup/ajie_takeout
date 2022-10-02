package com.ajie.service.impl;

import com.ajie.entity.Employee;
import com.ajie.mapper.EmployeeMapper;
import com.ajie.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author:hyj
 * @date:2022/10/1
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
