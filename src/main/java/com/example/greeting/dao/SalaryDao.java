package com.example.greeting.dao;

import com.example.greeting.dto.AttendanceDto;
import com.example.greeting.dto.EmployeeDto;
import com.example.greeting.dto.SalaryDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface SalaryDao {

    @Select("SELECT a.employee_id, a.user_name, " +
            "p.position_name AS position, d.department_name AS department, " +
            "MAX(a.start_time) AS start_time, " +
            "MAX(a.end_time) AS end_time, " +
            "DATE_FORMAT(MAX(a.start_time), '%Y-%m-%d') AS payment_date " +
            "FROM attendance a " +
            "JOIN department_td d ON a.department = d.department " +  // department_td와 조인
            "JOIN position_td p ON a.position = p.position " +  // position_td와 조인
            "WHERE MONTH(a.start_time) = #{month} AND YEAR(a.start_time) = #{year} " +
            "GROUP BY a.employee_id, a.user_name, p.position_name, d.department_name")
    List<AttendanceDto> getSalaryList(int month, int year) throws DataAccessException;

//    @Select("SELECT s.employee_id, s.user_name, s.department, s.position, s.daily_wage, s.additional_wage, s.position_wage, \n" +
//            "       s.income_tax, s.resident_tax, s.national_pension, s.health_insurance, s.employ_insurance, s.tot_salary, \n" +
//            "       s.tot_tribute, s.real_number, s.payment_date \n" +
//            "FROM salary s \n" +
//            "WHERE s.employee_id = #{employee_id}")
//    SalaryDto findAttendanceByEmployeeId(int employee_id) throws DataAccessException;

    // 기존 쿼리에서 year와 month를 추가하여 특정 달의 급여를 가져오도록 수정
//    @Select("SELECT s.employee_id, s.user_name, s.department, s.position, s.daily_wage, s.additional_wage, s.position_wage, " +
//            "s.income_tax, s.resident_tax, s.national_pension, s.health_insurance, s.employ_insurance, s.tot_salary, " +
//            "s.tot_tribute, s.real_number, s.payment_date " +
//            "FROM salary s " +
//            "WHERE s.employee_id = #{employee_id} " +
//            "AND YEAR(s.payment_date) = #{year} " +
//            "AND MONTH(s.payment_date) = #{month}")
//    SalaryDto findAttendanceByEmployeeIdAndMonth(int employee_id, int year, int month) throws DataAccessException;

    @Select("SELECT s.employee_id, s.user_name, s.department, s.position, s.daily_wage, s.additional_wage, " +
            "s.position_wage, s.income_tax, s.resident_tax, s.national_pension, s.health_insurance, " +
            "s.employ_insurance, s.tot_salary, s.tot_tribute, s.real_number, s.payment_date " +
            "FROM salary s " +
            "WHERE s.employee_id = #{employee_id} AND YEAR(s.payment_date) = #{year} AND MONTH(s.payment_date) = #{month}")
    SalaryDto findAttendanceByEmployeeIdAndMonth(@Param("employee_id") int employee_id,
                                                 @Param("year") int year,
                                                 @Param("month") int month);

    @Select("SELECT s.employee_id, s.user_name, s.department, s.position, s.daily_wage, s.additional_wage, " +
            "s.position_wage, s.income_tax, s.resident_tax, s.national_pension, s.health_insurance, " +
            "s.employ_insurance, s.tot_salary, s.tot_tribute, s.real_number, s.payment_date " +
            "FROM salary s " +
            "WHERE MONTH(s.payment_date) = #{month} AND YEAR(s.payment_date) = #{year} " +
            "AND s.user_name LIKE CONCAT('%', #{searchName}, '%')")
    List<SalaryDto> getSalaryListByMonthAndName(@Param("month") int month,
                                                @Param("year") int year,
                                                @Param("searchName") String searchName);
}