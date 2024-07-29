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
}
