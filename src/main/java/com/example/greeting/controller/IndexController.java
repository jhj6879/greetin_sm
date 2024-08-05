package com.example.greeting.controller;

//import com.example.greeting.employee.service.EmployeeService;
import com.example.greeting.dto.EmployeeDto;
import com.example.greeting.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class IndexController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/")
    public String Home(){
        return "/index";
    }

    @GetMapping("/index")
    public ModelAndView getMemberInfo(Principal principal) { //세션에 기록된 userid를 가져옴
        ModelAndView mav = new ModelAndView("index"); //모델과 뷰를 한꺼번에 제어하는 클래스 1)뷰를 넘겨줌
        EmployeeDto dto = new EmployeeDto();
        dto = employeeService.getMemberInfo(principal.getName());
        mav.addObject("index", dto);

        return mav;
    }

    @GetMapping("/email")
    public String Email(){
        return "email";
    }

    @GetMapping("/sendemail")
    public String Sendemail(){
        return "sendemail";
    }

    @GetMapping("/calendar")
    public String Calendar(){
        return "calendar";
    }



    @GetMapping("/attendance")
    public String Attendance(){
        return "attendance";
    }

    @GetMapping("/salary")
    public String Salary(){
        return "salary";
    }


}
