package com.example.greeting.service;

import com.example.greeting.dao.EmployeeDao;
import com.example.greeting.dto.EmployeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeDao employeeDao;
    private final PasswordEncoder passwordEncoder;

    public EmployeeDto create(EmployeeDto dto) {
        dto.setUser_pw(passwordEncoder.encode(dto.getUser_pw()));
        this.employeeDao.insertEmployee(dto);
        return dto;
    }

    public EmployeeDto getMemberInfo(String user_id) {
        EmployeeDto dto = new EmployeeDto();
        dto = employeeDao.getByUserId(user_id);
        return dto;
    }
}
