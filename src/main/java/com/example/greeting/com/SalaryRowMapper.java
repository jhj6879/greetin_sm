package com.example.greeting.com;

import com.example.greeting.dto.SalaryDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SalaryRowMapper implements RowMapper<SalaryDto> {

    @Override
    public SalaryDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        SalaryDto salary = new SalaryDto();

        // ResultSet에서 데이터를 가져와 SalaryDto 객체에 설정
        salary.setSalary_id(rs.getInt("salary_id"));
        salary.setEmployee_id(rs.getInt("employee_id"));
        salary.setUser_name(rs.getString("user_name"));
        salary.setDepartment(rs.getString("department"));
        salary.setPosition(rs.getString("position"));
        salary.setDaily_wage(rs.getString("daily_wage"));
        salary.setAdditional_wage(rs.getString("additional_wage"));
        salary.setPosition_wage(rs.getString("position_wage"));
        salary.setIncome_tax(rs.getString("income_tax"));
        salary.setResident_tax(rs.getString("resident_tax"));
        salary.setNational_pension(rs.getString("national_pension"));
        salary.setHealth_insurance(rs.getString("health_insurance"));
        salary.setEmploy_insurance(rs.getString("employ_insurance"));
        salary.setPayment_date(rs.getDate("payment_date").toLocalDate());
        salary.setTot_salary(rs.getString("tot_salary"));
        salary.setTot_tribute(rs.getString("tot_tribute"));
        salary.setReal_number(rs.getString("real_number"));

        return salary;
    }
}