package com.example.greeting.controller;

//import com.example.greeting.employee.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IndexController {
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

    @GetMapping("/notice")
    public String Notice(Model model, @PathVariable("post_no") int post_no){
//        List<NoticeDto> list = service.getNoticeList(post_no);
//        model.addAttribute("list", list);
        return "notice";
    }

    @GetMapping("/write")
    public String Wriet(){
        return "/write";
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
