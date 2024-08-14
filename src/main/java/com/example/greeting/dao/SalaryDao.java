package com.example.greeting.dao;

import com.example.greeting.dto.AttendanceDto;
import com.example.greeting.dto.EmployeeDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface SalaryDao {

    @Select("SELECT employee_id, user_name, position, department, " +
            "MAX(start_time) AS start_time, " +
            "MAX(end_time) AS end_time, " +
            "DATE_FORMAT(MAX(start_time), '%Y-%m-%d') AS payment_date " +
            "FROM attendance " +
            "WHERE MONTH(start_time) = #{month} AND YEAR(start_time) = #{year} " +
            "GROUP BY employee_id, user_name, position, department")
    List<AttendanceDto> getSalaryList(int month, int year) throws DataAccessException;

    @Select("SELECT * FROM attendance WHERE employee_id = #{employee_id}")
    List<AttendanceDto> findAttendanceByEmployeeId(int employee_id) throws DataAccessException;

}
