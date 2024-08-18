package com.example.greeting.controller;

import com.example.greeting.dto.DepartmentDto;
import com.example.greeting.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    // 부서 리스트 조회
    @GetMapping("/department")
    public String getDepartmentList(Model model) {
        List<DepartmentDto> departmentList = departmentService.getDepartmentList();
        model.addAttribute("departmentList", departmentList);
        return "department";  // department_list.html로 반환
    }

    // 부서 추가 처리
    @PostMapping("/departments/add")
    public String addDepartment(@ModelAttribute DepartmentDto departmentDto) {
        departmentService.addDepartment(departmentDto);
        return "redirect:/department";
    }

    // 부서 수정 처리
    @PostMapping("/departments/update")
    public String updateDepartment(@ModelAttribute DepartmentDto departmentDto) {
        departmentService.updateDepartment(departmentDto);
        return "redirect:/department";
    }

    // 부서 삭제 처리
    @PostMapping("/departments/delete")
    public String deleteDepartment(@RequestParam("department") String department) {
        departmentService.deleteDepartment(department);
        return "redirect:/department";
    }
}
