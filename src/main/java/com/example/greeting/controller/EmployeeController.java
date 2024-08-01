package com.example.greeting.controller;

import com.example.greeting.dto.EmployeeDto;
import com.example.greeting.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
@RequiredArgsConstructor
@Controller
public class EmployeeController {

    private final EmployeeService employeeService;

    // 사원관리
    @GetMapping("/employee")
    public String Employee(){
        return "employee";
    }

    // 부서관리
    @GetMapping("/department")
    public String Department(){
        return "department";
    }

    // 직책관리
    @GetMapping("/position")
    public String Position(){
        return "position";
    }

    // 로그인 페이지
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // 로그인 후 홈화면 이동
//    @PostMapping("/login")
//    public String LoginInfo(@RequestParam (value="user_id") String user_id,
//                            @RequestParam (value="user_pw") String user_pw) {
//        //서비스에게 요청
//        if(employeeService.checkLogin(user_id, user_pw)) {
//            return "index";
//        }
//        return "login";
//    }


    // 회원가입 페이지
    @GetMapping("/join")
    public String JoinPage(){
        return "join";
    }

    // 회원가입 정보 DB로 넘기기
    @PostMapping("/join") //DB에 저장
    public String joinAply(EmployeeDto dto) {
        employeeService.create(dto);
        return "login";
    }

    @GetMapping("/checkid")
    @ResponseBody
    public String checkId(@RequestParam(value="data") String user_id) {
        return String.valueOf(employeeService.memberId(user_id));
    }

}