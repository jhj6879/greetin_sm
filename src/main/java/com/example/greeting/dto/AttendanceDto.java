package com.example.greeting.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AttendanceDto {

    private int employee_id;
    private String user_name;
    private String department;
    private String position;
    private String start_time;
    private String end_time;
    private String over_time;// 초과 근무 시간
    private String holi_day;
    private String user_id;

    private Double working_hours;  // 총 근무 시간
    private Double latency_time;   // 지각 시간
    private Double early_leave_time; // 조퇴 시간

    private LocalDate work_date;  // 날짜 필드 추가
    private String attendance_date;  // 날짜 필드 추가

    // 근무 일수를 계산하여 저장하기 위한 필드
    private int work_days;  // 근무 일수 필드 추가

    public int getWorkingHours() {
        return 0;
    }
}
