package com.example.greeting.domain;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Hom {
    @GetMapping("/hom")
    public String HomPage(){
        return "/hom";
    }
}
