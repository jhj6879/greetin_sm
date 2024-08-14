package com.example.greeting.service;

import com.example.greeting.dao.SalaryDao;
import com.example.greeting.dto.AttendanceDto;
import com.example.greeting.dto.EmployeeDto;
import com.example.greeting.dto.SalaryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class SalaryService {
    @Autowired
    private SalaryDao salaryDao;

    public List<SalaryDto> getSalaryList(int month, int year) {
        List<AttendanceDto> attendanceList = salaryDao.getSalaryList(month, year);
//        System.out.println("Attendance List: " + attendanceList);
        return calculateSalary(attendanceList);
    }

    public SalaryDto calculateSalary(int employee_id) {
        List<AttendanceDto> attendanceList = salaryDao.findAttendanceByEmployeeId(employee_id);
        return calculateSalaryFromAttendance(attendanceList.get(0)); // 첫 번째 출근 기록 사용
    }

    private List<SalaryDto> calculateSalaryList(List<AttendanceDto> attendanceList) {
        List<SalaryDto> salaryList = new ArrayList<>();

        for (AttendanceDto attendance : attendanceList) {
            SalaryDto salary = calculateSalaryFromAttendance(attendance);
            salaryList.add(salary);
        }

        return salaryList;
    }

    private SalaryDto calculateSalaryFromAttendance(AttendanceDto attendance) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 급여 정보 계산
        SalaryDto salary = new SalaryDto();
        salary.setEmployee_id(attendance.getEmployee_id());
        salary.setUser_name(attendance.getUser_name());
        salary.setDepartment(attendance.getDepartment());
        salary.setPosition(attendance.getPosition());

        // 하루 일당을 설정 (예시)
        int dailyWage = 2000000;
        salary.setDaily_wage(String.valueOf(dailyWage));

        // 세금 및 공제 항목 계산
        int incomeTax = dailyWage * 10 / 100;
        int healthInsurance = dailyWage * 5 / 100;
        int totalDeductions = incomeTax + healthInsurance;

        salary.setIncome_tax(String.valueOf(incomeTax));
        salary.setHealth_insurance(String.valueOf(healthInsurance));
        salary.setTot_salary(String.valueOf(dailyWage));
        salary.setTot_tribute(String.valueOf(totalDeductions));
        salary.setReal_number(String.valueOf(dailyWage - totalDeductions));

        // 급여 지급일을 설정 (예시로 출근 기록의 날짜 사용)
        LocalDateTime latestDate = LocalDateTime.parse(attendance.getStart_time(), formatter);
        salary.setPayment_date(latestDate.toLocalDate());

        return salary;
    }


    private List<SalaryDto> calculateSalary(List<AttendanceDto> attendanceList) {
        List<SalaryDto> salaryList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (AttendanceDto attendance : attendanceList) {
            // 사원의 출근 기록 중 마지막 날짜를 찾음
            LocalDateTime latestDate = attendanceList.stream()
                    .filter(a -> a.getEmployee_id() == attendance.getEmployee_id())
                    .map(a -> LocalDateTime.parse(a.getStart_time(), formatter))
                    .max(Comparator.naturalOrder())
                    .orElse(LocalDateTime.now());

            // 급여 정보 계산
            SalaryDto salary = new SalaryDto();
            salary.setEmployee_id(attendance.getEmployee_id());
            salary.setUser_name(attendance.getUser_name());
            salary.setDepartment(attendance.getDepartment());
            salary.setPosition(attendance.getPosition());

            // 하루 일당을 설정 (예시)
            int dailyWage = 2000000;
            salary.setDaily_wage(String.valueOf(dailyWage));

            // 세금 및 공제 항목 계산
            int incomeTax = dailyWage * 10 / 100;
            int healthInsurance = dailyWage * 5 / 100;
            int totalDeductions = incomeTax + healthInsurance;

            salary.setIncome_tax(String.valueOf(incomeTax));
            salary.setHealth_insurance(String.valueOf(healthInsurance));
            salary.setTot_salary(String.valueOf(dailyWage));
            salary.setTot_tribute(String.valueOf(totalDeductions));
            salary.setReal_number(String.valueOf(dailyWage - totalDeductions));

            // 가장 늦은 출근 날짜를 급여 지급일로 설정
            salary.setPayment_date(latestDate.toLocalDate());

            salaryList.add(salary);
        }

        return salaryList;
    }


}
