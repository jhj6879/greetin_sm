package com.example.greeting.controller;

import com.example.greeting.dto.AttendanceDto;
import com.example.greeting.dto.AttendanceRequest;
import com.example.greeting.dto.EmployeeDto;
import com.example.greeting.dto.LeaveDto;
import com.example.greeting.service.AttendanceService;
import com.example.greeting.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/record-time")
    public ResponseEntity<String> recordTime(@RequestBody AttendanceRequest request, Principal principal) {
        try {
            String userId = principal.getName();  // userId를 직접 가져옴
            System.out.println("Found userId: " + userId);

            // userId는 String이므로 숫자로 변환하지 않음
            // user_name을 가져오기 위해 employeeService 호출
            EmployeeDto employeeDto = employeeService.getEmployeeById(userId);
            if (employeeDto == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                        body("{\"message\": \"Employee not found\"}");
            }

            // 가져온 정보를 AttendanceDto에 설정
            AttendanceDto att = new AttendanceDto();
            att.setUser_id(employeeDto.getUser_id());
            att.setEmployee_id(employeeDto.getEmployee_id());  // employee_id 설정
            att.setUser_name(employeeDto.getUser_name());
            att.setDepartment(employeeDto.getDepartment());
            att.setPosition(employeeDto.getPosition());

            // MySQL 형식을 LocalDateTime으로 변환하기 위한 포매터
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime parsedTime = LocalDateTime.parse(request.getTime(), formatter);
            // 다시 MySQL 형식으로 변환
            String formattedTime = parsedTime.format(formatter);

            att.setAttendance_date(String.valueOf(parsedTime.toLocalDate()));

            // 출근/퇴근 시간 설정
            if (request.getAction().equals("clockIn")) {
                att.setStart_time(request.getTime());
            } else if (request.getAction().equals("clockOut")) {
                att.setEnd_time(request.getTime());
            }

            // 출근/퇴근 시간 저장
            if (request.getAction().equals("clockIn")) {
                attendanceService.save(att);
            }else if (request.getAction().equals("clockOut")) {
                attendanceService.recordClockOutOrHoliday(att);
            }

            return ResponseEntity.ok("{\"message\": \"Time recorded successfully\"}");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"Error recording time\"}");
        }
    }

    // 근테 관리
    @GetMapping("/attendance")
    public String getAttendance(@RequestParam(value = "yearMonth", required = false) String yearMonth,
                                @RequestParam(value = "userName", required = false) String userName, Model model) {

        // 기본적으로 현재 년/월로 설정, 값이 없을 경우 기본 값 설정
        if (yearMonth == null) {
            yearMonth = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        }

        List<AttendanceDto> list = attendanceService.getAttendanceByMonthAndName(yearMonth, userName);
        model.addAttribute("list", list);
        model.addAttribute("yearMonth", yearMonth);
        model.addAttribute("userName", userName);

        return "attendance";
    }

    @GetMapping("/leave_application")
    public String LeaveAppli(Model model) {
        // 현재 로그인한 사용자의 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();  // 로그인한 사용자의 user_id 가져오기
        System.out.println("User ID from authentication: " + userId);

        // user_id를 통해 employee_id와 관련 정보를 조회
        EmployeeDto employeeDto = employeeService.getEmployeeId(userId);

        if (employeeDto != null) {
            System.out.println("Fetched Employee ID: " + employeeDto.getEmployee_id());
            // LeaveDto 객체 생성 및 필요한 정보 설정
            LeaveDto dto = new LeaveDto();
            dto.setEmployee_id(employeeDto.getEmployee_id());
            dto.setUser_name(employeeDto.getUser_name());  // 유저 이름 설정
            dto.setDepartment(employeeDto.getDepartment());  // 부서 설정
            // 나머지 필드는 null 상태로 유지


            model.addAttribute("dto", dto);
        } else {
            // 오류 처리: EmployeeDto가 null인 경우
            model.addAttribute("error", "Employee information not found");
        }

        return "leave_application";  // leave_application.html 템플릿 렌더링
    }


    @PostMapping("/applyLeave")
    public String applyLeave(@ModelAttribute LeaveDto leaveDto) {
        System.out.println("LeaveDto: " + leaveDto);
        System.out.println("Employee ID: " + leaveDto.getEmployee_id());
        attendanceService.applyLeave(leaveDto);
        return "redirect:/";
    }


}