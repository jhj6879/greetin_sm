package com.example.greeting.controller;

import com.example.greeting.dto.EmailDto;
import com.example.greeting.service.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EmailController {

    private final EmailService email;

    EmailController(EmailService email){
        this.email=email;
    }

    // 관리자 유저 모두 사용
    @GetMapping("/email")
    public String Email(){
        return "email";
    }

    @GetMapping("/sendemail")
    public String SendemailPage(){
        return "sendemail";
    }

    @PostMapping("/sendemail")
    public String sendemailPost(EmailDto msg){

        if(email.sendMailReject(msg)){
            System.out.println("Email 전송 시작!");
        }else System.out.println("실패");

        return "redirect:/";


    }


}

