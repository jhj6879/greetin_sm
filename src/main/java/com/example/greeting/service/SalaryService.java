package com.example.greeting.service;

import com.example.greeting.dao.SalaryDao;
import com.example.greeting.dao.SalaryDaoImpl;
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

    @Autowired
    private SalaryDaoImpl salaryDaoImpl;  // 새로 추가된 구현 클래스

    // 년 월 사원검색
    public List<SalaryDto> getSalaryListByMonthAndName(int month, int year, String searchName) {
        return salaryDao.getSalaryListByMonthAndName(month, year, searchName);
    }

    // 월별로 생성된 급여 리스트를 가져오는 메서드
    public List<SalaryDto> getSalaryList(int month, int year) {
        List<AttendanceDto> attendanceList = salaryDao.getSalaryList(month, year);
        return calculateSalaryList(attendanceList);
    }

//    public List<SalaryDto> getSalaryList(int month, int year) {
//        List<AttendanceDto> attendanceList = salaryDao.getSalaryList(month, year);
////        System.out.println("Attendance List: " + attendanceList);
//        return calculateSalary(attendanceList);
//    }

//    public SalaryDto calculateSalary(int employee_id) {
//        return salaryDao.findAttendanceByEmployeeId(employee_id);
//    }

    // 기존 메서드: 특정 사원의 급여를 조회하는 메서드
    // 새로운 파라미터로 year와 month를 추가하여 특정 달의 급여를 조회할 수 있도록 수정
    public SalaryDto calculateSalary(int employee_id, int year, int month) {
        System.out.println("Calculating salary for employee_id: " + employee_id + ", year: " + year + ", month: " + month);

        SalaryDto salary = salaryDao.findAttendanceByEmployeeIdAndMonth(employee_id, year, month);

        if (salary == null) {
            System.out.println("해당 월에 대한 급여 데이터가 없습니다. 사원 ID: " + employee_id + ", 연도: " + year + ", 월: " + month);
            return new SalaryDto(); // 빈 객체 반환 또는 사용자에게 알림
        }

        return salary;
    }


    // 월별 급여 데이터를 생성하는 메서드
    public void generateMonthlySalaryData(int year, int month) {
        salaryDaoImpl.generateMonthlySalaryData(year, month);  // 새로운 기능 호출
    }

    // 급여 리스트 계산
    private List<SalaryDto> calculateSalaryList(List<AttendanceDto> attendanceList) {
        List<SalaryDto> salaryList = new ArrayList<>();

        for (AttendanceDto attendance : attendanceList) {
            SalaryDto salary = calculateSalaryFromAttendance(attendance);
            salaryList.add(salary);
        }

        return salaryList;
    }

    // 급여 계산
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