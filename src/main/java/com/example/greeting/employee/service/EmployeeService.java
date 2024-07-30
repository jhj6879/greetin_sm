package com.example.greeting.employee.service;

import com.example.greeting.employee.dao.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeDao employeeDao;
}
