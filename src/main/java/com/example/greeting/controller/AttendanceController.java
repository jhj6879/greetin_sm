package com.example.greeting.controller;

import com.example.greeting.dto.AttendanceDto;
import com.example.greeting.dto.AttendanceRequest;
import com.example.greeting.dto.EmployeeDto;
import com.example.greeting.service.AttendanceService;
import com.example.greeting.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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


    // 근태관리 페이지(관리자용)
    @GetMapping("/attendance")
    public String Attendance(@RequestParam(value = "employee_id", required = false) Integer employee_id, Model model) {
        List<AttendanceDto> list;
        if (employee_id != null) {
            list = attendanceService.getAttendanceList(employee_id);
        } else {
            list = attendanceService.getAllAttendanceList(); // 모든 데이터를 가져오는 메서드
        }
        model.addAttribute("list", list);

        // 예를 들어, DAO에서 가져온 결과를 처리할 때
        AttendanceDto attendanceDto = new AttendanceDto();
        int workingHours = attendanceDto.getWorkingHours(); // DB에서 가져온 정수 값
        String workingHoursStr = workingHours + "시간"; // Java에서 "시간" 단위 추가

        return "attendance";
    }

    @GetMapping("/leave_application")
    public String LeaveAppli(){
        return "leave_application";
    }

}
