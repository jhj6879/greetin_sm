package com.example.greeting.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeDto {
    private String user_id;
    private String user_pw;
    private int employee_id;
    private String user_name;
    private int r_num;
    private String tel;
    private String address;
    private String email;
    private String gender;
    private String department;
    private String position;
    private String hire_date;
    private String resignation_date;
    private String employment;
    private String permit;

}
