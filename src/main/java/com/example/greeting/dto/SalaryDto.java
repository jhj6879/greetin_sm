package com.example.greeting.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SalaryDto {
    private int salary_id;
    private int employee_id;
    private String  user_name;
    private String  department;
    private String  position;
    private String  daily_wage;
    private String  additional_wage;
    private String  position_wage;
    private String  income_tax;
    private String  resident_tax;
    private String  national_pension;
    private String  health_insurance;
    private String  employ_insurance;
}
