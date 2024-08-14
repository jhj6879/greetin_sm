package com.example.greeting.controller;

import com.example.greeting.dto.AttendanceDto;
import com.example.greeting.dto.EmployeeDto;
import com.example.greeting.dto.SalaryDto;
import com.example.greeting.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    // 관리자는 모든 직원 급여 관리
    @GetMapping("/salary")
    public String SalaryPage(@RequestParam(value = "month", required = false) Integer month,
                             @RequestParam(value = "year", required = false) Integer year,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        if (month == null || year == null) {
            redirectAttributes.addAttribute("month", 7); // 기본값 설정
            redirectAttributes.addAttribute("year", 2024); // 기본값 설정
            return "redirect:/salary";
        }

        List<SalaryDto> list = salaryService.getSalaryList(month, year);
        model.addAttribute("list", list);

        return "salary";
    }

    // 클릭한 사원에 대한 상세한 급여 명세서 조회
    @GetMapping("/salary/{employee_id}")
    public String getEmployeeDetails(@PathVariable int employee_id, Model model) {
        // 사원의 근태 정보 기반으로 급여 계산
        SalaryDto salary = salaryService.calculateSalary(employee_id);

        if (salary == null) {
            salary = new SalaryDto(); // 빈 객체를 생성하여 null 방지
        }

        model.addAttribute("salary", salary);
        return "salary :: salaryDetailsFragment";
    }



}
