package com.example.greeting.service;

import com.example.greeting.dao.EmployeeDao;
import com.example.greeting.dto.EmployeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    public boolean memberId(String user_id) {
        boolean result = false;
        if(employeeDao.checkId(user_id) == 0) {
            result = true;
        }
        return result;
    }

}