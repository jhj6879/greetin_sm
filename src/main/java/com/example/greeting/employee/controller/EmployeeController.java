package com.example.greeting.employee.controller;

import com.example.greeting.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employee")
    public String Employee(){
        return "/employee";
    }

    @GetMapping("/department")
    public String Department(){
        return "/department";
    }

    @GetMapping("/position")
    public String Position(){
        return "/position";
    }

    @GetMapping("/login")
    public String Login(){
        return "/login";
    }
}
