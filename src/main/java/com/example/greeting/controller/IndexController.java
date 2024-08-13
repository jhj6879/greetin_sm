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

    @GetMapping("/")
    public String Home(Model model){
        List<PostDto> list = postService.selectRecentPosts();
        model.addAttribute("list", list);

        List<LeaveDto> leaveList = attendanceService.selectRecentPosts();
        model.addAttribute("leaveList", leaveList);
        return "/index";
    }

//    @GetMapping("/")
//    public String HomeLeave(Model model){
//        List<LeaveDto> leaveList = attendanceService.selectRecentPosts();
//        model.addAttribute("list", leaveList);
//        return "/index";
//    }

    @GetMapping("/index")
    public ModelAndView getMemberInfo(Principal principal) {
        ModelAndView mav = new ModelAndView("index");

        EmployeeDto employeeDto = employeeService.getMemberInfo(principal.getName());
        mav.addObject("employee", employeeDto);

        AttendanceDto attendanceDto = attendanceService.getTime(principal.getName());
        mav.addObject("attendance", attendanceDto);

        return mav;
    }

    // 관리자 유저 모두 사용
    @GetMapping("/email")
    public String Email(){
        return "email";
    }

    @GetMapping("/sendemail")
    public String Sendemail(){
        return "sendemail";
    }

    // 관지라 유저 모두 사용 (추후 시간 가능하면)
    @GetMapping("/calendar")
    public String Calendar(){
        return "calendar";
    }

    // 관리자는 모든 직원 급여 관리
    // 유저는 자신의 급여 한달치 조회 가능하게
    @GetMapping("/salary")
    public String Salary(){
        return "salary";
    }


}
