package com.example.greeting.controller;

import com.example.greeting.dto.EmployeeDto;
import com.example.greeting.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/employee")
    public String Employee(){
        return "employee";
    }

    @GetMapping("/department")
    public String Department(){
        return "department";
    }

    @GetMapping("/position")
    public String Position(){
        return "position";
    }

    @GetMapping("/login")
    public String Login(){
        return "login";
    }
    @GetMapping("/join")
    public String JoinPage(){
        return "join";
    }

    @PostMapping("/join") //DB에 저장
    public String joinAply(EmployeeDto dto) {
        employeeService.create(dto);
        return "index";
    }

}
