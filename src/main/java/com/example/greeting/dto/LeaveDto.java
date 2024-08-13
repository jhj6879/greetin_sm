package com.example.greeting.dto;

import lombok.*;
import org.apache.ibatis.annotations.Select;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LeaveDto {
    private int leave_id;
    private int employee_id;
    private String user_name;
    private String department;
    private String holi_day;
    private String start_day;
    private String end_day;
    private String leave_reason;
}
