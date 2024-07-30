package com.example.greeting.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String Home(){
        return "/index";
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



    @GetMapping("/attendance")
    public String Attendance(){
        return "/attendance";
    }

    @GetMapping("/salary")
    public String Salary(){
        return "/salary";
    }


}
