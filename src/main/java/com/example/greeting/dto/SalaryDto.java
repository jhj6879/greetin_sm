package com.example.greeting.dto;

import lombok.*;

import java.time.LocalDate;

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

    private LocalDate payment_date; // 새로 추가

    private String tot_salary;  // 총 급여
    private String tot_tribute;  // 총 새액
    private String real_number;

    // 근무 일수를 계산하여 저장하기 위한 필드
    private int work_days;  // 근무 일수 필드 추가
}
