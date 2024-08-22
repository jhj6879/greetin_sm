package com.example.greeting.controller;

//import com.example.greeting.employee.service.EmployeeService;
import com.example.greeting.dto.AttendanceDto;
import com.example.greeting.dto.EmployeeDto;
import com.example.greeting.dto.LeaveDto;
import com.example.greeting.dto.PostDto;
import com.example.greeting.service.AttendanceService;
import com.example.greeting.service.EmployeeService;
import com.example.greeting.service.FileService;
import com.example.greeting.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private AttendanceService attendanceService;

    // 홈 화면에서 공지사항, 휴가
    @GetMapping("/")
    public String Home(Model model){
        List<PostDto> list = postService.selectRecentPosts();
        model.addAttribute("list", list);

        List<LeaveDto> leaveList = attendanceService.selectRecentPosts();
        model.addAttribute("leaveList", leaveList);
        return "/index";
    }

    // 홈 화면에서 로그인 한 회원 출, 퇴근 기록 넘기기
    @GetMapping("/index")
    public ModelAndView getMemberInfo(Principal principal) {
        ModelAndView mav = new ModelAndView("index");

        EmployeeDto employeeDto = employeeService.getMemberInfo(principal.getName());
        mav.addObject("employee", employeeDto);

        AttendanceDto attendanceDto = attendanceService.getTime(principal.getName());
        mav.addObject("attendance", attendanceDto);

        return mav;
    }

}
