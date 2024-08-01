package com.example.greeting.controller;

import com.example.greeting.dto.EmployeeDto;
import com.example.greeting.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;


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

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/index")
    public ModelAndView getMemberInfo(Principal principal) {
        ModelAndView mav = new ModelAndView("index");
        EmployeeDto dto = employeeService.getMemberInfo(principal.getName());
        mav.addObject("index", dto);
        return mav;
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
