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

        // 월별 급여 데이터를 생성
        salaryService.generateMonthlySalaryData(year, month);

        // 생성된 데이터를 조회
        List<SalaryDto> list = salaryService.getSalaryList(month, year);
        model.addAttribute("list", list);

        // 기본적으로 첫 번째 사원의 급여 표시
        if (!list.isEmpty()) {
            SalaryDto dto = salaryService.calculateSalary(list.get(0).getEmployee_id(), year, month);
            model.addAttribute("dto", dto);
        }else {
            // 빈 객체를 모델에 추가하여 템플릿에서 오류가 발생하지 않도록 처리
            model.addAttribute("dto", new SalaryDto());
        }

        return "salary";
    }

    // 클릭한 사원에 대한 상세한 급여 명세서 조회
    @GetMapping("/salary/{employee_id}")
    public String getEmployeeDetails(@PathVariable int employee_id,
                                     @RequestParam("year") int year,
                                     @RequestParam("month") int month,
                                     Model model) {
        try {
            SalaryDto salary = salaryService.calculateSalary(employee_id, year, month);
            model.addAttribute("dto", salary);
            return "salary :: salaryDetailsFragment";  // 프래그먼트를 반환
        } catch (Exception e) {
            e.printStackTrace();  // 서버 로그에 예외 출력
            return "error";  // 오류가 발생한 경우 오류 페이지 반환
        }
    }
}