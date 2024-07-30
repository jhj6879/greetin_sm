package com.example.greeting.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String Home(){
        return "/index";
    }

    @GetMapping("/notice")
    public String Notice(){
        return "/notice";
    }

    @GetMapping("/email")
    public String Email(){
        return "/email";
    }

    @GetMapping("/sendemail")
    public String Sendemail(){
        return "/sendemail";
    }

    @GetMapping("/calendar")
    public String Calendar(){
        return "/calendar";
    }

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

    @GetMapping("/attendance")
    public String Attendance(){
        return "/attendance";
    }

    @GetMapping("/salary")
    public String Salary(){
        return "/salary";
    }

    @GetMapping("/login")
    public String Login(){
        return "/login";
    }
}
