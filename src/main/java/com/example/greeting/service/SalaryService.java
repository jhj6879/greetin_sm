package com.example.greeting.service;

import com.example.greeting.dao.AttendanceDao;
import com.example.greeting.dao.SalaryDao;
import com.example.greeting.dao.SalaryDaoImpl;
import com.example.greeting.dto.AttendanceDto;
import com.example.greeting.dto.EmployeeDto;
import com.example.greeting.dto.SalaryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Service
public class SalaryService {

    @Autowired
    private AttendanceDao attendanceDao;

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

    // 특정 사원의 급여를 계산하는 메서드 (기존 메서드 수정)
    public SalaryDto calculateSalary(int employee_id, int year, int month) {
        // 기존 SalaryDto를 데이터베이스에서 가져옴
        SalaryDto salary = salaryDao.findAttendanceByEmployeeIdAndMonth(employee_id, year, month);

        // 해당 데이터가 없을 경우 기본 SalaryDto를 반환
        if (salary == null) {
            salary = new SalaryDto();
            salary.setEmployee_id(employee_id);
            return salary;
        }

        // 근무일수 계산
        int workDays = attendanceDao.countWorkDays(employee_id, year, month);

        // 일당 7만원 설정
        int dailyWage = 100000;

        // 기본급 계산
        int basicSalary = dailyWage * workDays;  // 근무일수에 따라 기본급 계산

        // 천 단위 콤마 포맷터 설정 (Locale을 한국으로 설정)
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.KOREA);

        // 기본급을 명확하게 반영하고 천 단위 콤마 포맷 적용
        salary.setDaily_wage(numberFormat.format(basicSalary));  // 기본급 반영
        salary.setWork_days(workDays);  // 근무일수 설정

        // 직책수당 및 추가수당 처리 (콤마 제거 후 파싱)
        int additionalWage = 0;
        int positionWage = 0;

        if (salary.getAdditional_wage() != null && !salary.getAdditional_wage().isEmpty()) {
            additionalWage = Integer.parseInt(salary.getAdditional_wage().replace(",", ""));
        }

        if (salary.getPosition_wage() != null && !salary.getPosition_wage().isEmpty()) {
            positionWage = Integer.parseInt(salary.getPosition_wage().replace(",", ""));
        }

        // 총 지급액 계산
        int totalSalary = basicSalary + additionalWage + positionWage;
        salary.setTot_salary(numberFormat.format(totalSalary));  // 총 급여에 천 단위 콤마 적용

        // 세금 및 공제 계산 (임의의 비율 적용)
        int incomeTax = (int) (totalSalary * 0.033); // 예: 소득세 3.3%
        int residentTax = (int) (incomeTax * 0.1);   // 주민세는 소득세의 10%
        int nationalPension = (int) (totalSalary * 0.045); // 국민연금 4.5%
        int healthInsurance = (int) (totalSalary * 0.033); // 건강보험 3.3%
        int employInsurance = (int) (totalSalary * 0.008); // 고용보험 0.8%

        // 총 공제액 계산
        int totalDeductions = incomeTax + residentTax + nationalPension + healthInsurance + employInsurance;

        salary.setTot_tribute(numberFormat.format(totalDeductions));  // 총 공제액에 천 단위 콤마 적용

        // 실 수령액 계산
        int realSalary = totalSalary - totalDeductions;
        salary.setReal_number(numberFormat.format(realSalary));  // 실 수령액에 천 단위 콤마 적용

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

        // 기본급 계산 로직
        int dailyWage = 70000;  // 하루 일당 7만원
        int basicWage = dailyWage * salary.getWork_days();  // 근무 일수 * 하루 일당 = 기본급

        // 직책수당, 초과근무수당 설정 (예시로 임의의 값을 설정함)
        int positionWage = Integer.parseInt(salary.getPosition_wage());
        int additionalWage = Integer.parseInt(salary.getAdditional_wage());

        // 총 지급액 계산
        int totalSalary = basicWage + positionWage + additionalWage;
        salary.setTot_salary(String.valueOf(totalSalary));

        // 세금 및 공제 항목 계산 (임의로 4.5%, 3.3%, 10% 비율 적용)
        int nationalPension = (int)(totalSalary * 0.0045);
        int healthInsurance = (int)(totalSalary * 0.033);
        int employInsurance = (int)(totalSalary * 0.008);
        int incomeTax = 3210000;  // 예시로 설정된 소득세 값
        int residentTax = 105930;  // 예시로 설정된 주민세 값

        // 총 공제액 계산
        int totalDeductions = nationalPension + healthInsurance + employInsurance + incomeTax + residentTax;
        salary.setTot_tribute(String.valueOf(totalDeductions));

        // 실 수령액 계산
        int realSalary = totalSalary - totalDeductions;
        salary.setReal_number(String.valueOf(realSalary));

        // 급여 지급일 설정
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