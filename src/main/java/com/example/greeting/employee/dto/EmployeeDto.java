package com.example.greeting.employee.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeDto {
    private String user_id;
    private String password;
    private int employee_id;
    private String name;
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
