package com.example.greeting.controller;

import com.example.greeting.com.Search;
import com.example.greeting.dto.EmployeeDto;
import com.example.greeting.dto.PostDto;
import com.example.greeting.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    // 사원관리(관리자용)
    // 기존 사원 목록 페이지
    @GetMapping("/employee")
    public String Employee(Model model, @RequestParam(value="keyword", required = false) String keyword) {
        List<EmployeeDto> list;

        if (keyword == null || keyword.trim().isEmpty()) {
            // 검색어가 없는 경우 전체 사원 목록
            list = employeeService.selectEmployeeList();
        } else {
            // 검색어가 있는 경우 해당 키워드로 검색된 사원 목록
            list = employeeService.searchEmployees(keyword);
        }

        model.addAttribute("list", list);

        // 기본적으로 첫 번째 사원의 상세정보 표시
        if (!list.isEmpty()) {
            EmployeeDto dto = employeeService.selectEmployee(list.get(0).getEmployee_id());
            model.addAttribute("dto", dto);
        }

        return "employee";
    }

    // 검색 기능을 위한 경로 (keyword에 따라 사원 목록을 필터링)
    @GetMapping("/employee/search")
    public String searchEmployee(@RequestParam("keyword") String keyword, Model model) {
        List<EmployeeDto> list = employeeService.searchEmployees(keyword);
        model.addAttribute("list", list);

        if (!list.isEmpty()) {
            EmployeeDto dto = employeeService.selectEmployee(list.get(0).getEmployee_id());
            model.addAttribute("dto", dto);
        }

        return "employee"; // 검색 후 employee.html 페이지로 결과를 반환
    }

    // 사원 상세 정보를 가져오는 새로운 메서드 추가
    @GetMapping("/employee/{employee_id}")
    public String getEmployeeDetails(@PathVariable int employee_id, Model model) {
        EmployeeDto dto = employeeService.selectEmployee(employee_id);
        model.addAttribute("dto", dto);
        return "employee :: employeeDetailsFragment";
    }

    // 사원정보 수정
    @PostMapping("/employee")
    public String updateEmployee(@ModelAttribute EmployeeDto dto) {
        employeeService.updateEmployee(dto);
        return "redirect:/employee";
    }

    // 로그인 페이지
    @GetMapping("/login")
    public String login() {
        return "login";  // src/main/resources/templates/login.html 반환
    }

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

    // 아이디 중복 확인
    @GetMapping("/checkid")
    @ResponseBody
    public String checkId(@RequestParam(value="data") String user_id) {
        return String.valueOf(employeeService.memberId(user_id));
    }

}