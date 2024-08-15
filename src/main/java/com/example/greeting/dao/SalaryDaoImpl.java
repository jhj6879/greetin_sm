package com.example.greeting.dao;

import com.example.greeting.com.SalaryRowMapper;
import com.example.greeting.dto.SalaryDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class SalaryDaoImpl {

    private final JdbcTemplate jdbcTemplate;

    // 생성자 주입을 통해 JdbcTemplate 주입
    public SalaryDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void generateMonthlySalaryData(int year, int month) {
        // 이미 해당 월의 데이터가 있는지 확인 (optional)
        String checkDataExistsSql =
                "SELECT COUNT(*) FROM salary " +
                        "WHERE YEAR(payment_date) = ? AND MONTH(payment_date) = ?";

        Integer count = jdbcTemplate.queryForObject(checkDataExistsSql, new Object[]{year, month}, Integer.class);

        if (count != null && count > 0) {
            // 데이터가 이미 존재하면 삽입하지 않음 또는 삭제 후 삽입
            System.out.println("Data already exists for year: " + year + " month: " + month);
            return; // 또는 기존 데이터를 삭제하고 새로운 데이터를 삽입하려면 아래 delete를 사용할 수 있음.

            // 만약 기존 데이터를 삭제하고 싶다면 아래 코드를 사용
        /*
        String deleteOldDataSql =
                "DELETE FROM salary WHERE YEAR(payment_date) = ? AND MONTH(payment_date) = ?";
        jdbcTemplate.update(deleteOldDataSql, year, month);
        */
        }

        // 1. 임시 테이블 생성
        String createTempTableSql =
                "CREATE TEMPORARY TABLE temp_monthly_attendance AS " +
                        "SELECT " +
                        "    a.employee_id, " +
                        "    a.user_name, " +
                        "    d.department_name AS department, " +
                        "    p.position_name AS position, " +
                        "    COUNT(a.attendance_date) AS work_days, " +
                        "    SUM(TIMESTAMPDIFF(HOUR, a.start_time, a.end_time)) AS total_work_hours " +
                        "FROM " +
                        "    attendance a " +
                        "JOIN department_td d ON a.department = d.department " +  // department_td와 조인
                        "JOIN position_td p ON a.position = p.position " +  // position_td와 조인
                        "WHERE " +
                        "    YEAR(a.start_time) = ? AND MONTH(a.start_time) = ? " +  // 대상 년도와 월
                        "GROUP BY " +
                        "    a.employee_id, a.user_name, d.department_name, p.position_name;";

        // 2. Salary 테이블에 데이터 삽입
        String insertSalaryDataSql =
                "INSERT INTO Salary (employee_id, user_name, department, position, daily_wage, additional_wage, position_wage, income_tax, resident_tax, national_pension, health_insurance, employ_insurance, tot_salary, tot_tribute, real_number, payment_date) " +
                        "SELECT " +
                        "    ma.employee_id, " +
                        "    ma.user_name, " +
                        "    ma.department, " +
                        "    ma.position, " +
                        "    70000 AS daily_wage,  " +
                        "    CASE WHEN ma.total_work_hours > (8 * ma.work_days) THEN (10000 * (ma.total_work_hours - 8 * ma.work_days)) ELSE 0 END AS additional_wage,  " +  // 추가 수당 계산
                        "    pt.position_wage,  " +
                        "    ((70000 * ma.work_days) + pt.position_wage + CASE WHEN ma.total_work_hours > (8 * ma.work_days) THEN (10000 * (ma.total_work_hours - 8 * ma.work_days)) ELSE 0 END) AS tot_salary,  " +  // 총 급여
                        "    (((70000 * ma.work_days) + pt.position_wage + CASE WHEN ma.total_work_hours > (8 * ma.work_days) THEN (10000 * (ma.total_work_hours - 8 * ma.work_days)) ELSE 0 END) * 0.033) AS income_tax,  " +  // 소득세 (3.3%)
                        "    ((((70000 * ma.work_days) + pt.position_wage + CASE WHEN ma.total_work_hours > (8 * ma.work_days) THEN (10000 * (ma.total_work_hours - 8 * ma.work_days)) ELSE 0 END) * 0.033) * 0.1) AS resident_tax,  " +  // 주민세 (소득세의 10%)
                        "    (((70000 * ma.work_days) + pt.position_wage + CASE WHEN ma.total_work_hours > (8 * ma.work_days) THEN (10000 * (ma.total_work_hours - 8 * ma.work_days)) ELSE 0 END) * 0.045) AS national_pension,  " +  // 국민연금 (4.5%)
                        "    (((70000 * ma.work_days) + pt.position_wage + CASE WHEN ma.total_work_hours > (8 * ma.work_days) THEN (10000 * (ma.total_work_hours - 8 * ma.work_days)) ELSE 0 END) * 0.0333) AS health_insurance,  " +  // 건강보험 (3.33%)
                        "    (((70000 * ma.work_days) + pt.position_wage + CASE WHEN ma.total_work_hours > (8 * ma.work_days) THEN (10000 * (ma.total_work_hours - 8 * ma.work_days)) ELSE 0 END) * 0.008) AS employ_insurance,  " +  // 고용보험 (0.8%)
                        "    (((70000 * ma.work_days) + pt.position_wage + CASE WHEN ma.total_work_hours > (8 * ma.work_days) THEN (10000 * (ma.total_work_hours - 8 * ma.work_days)) ELSE 0 END) * (0.033 + 0.0333 + 0.045 + 0.008) + (((70000 * ma.work_days) + pt.position_wage + CASE WHEN ma.total_work_hours > (8 * ma.work_days) THEN (10000 * (ma.total_work_hours - 8 * ma.work_days)) ELSE 0 END) * 0.033 * 0.1)) AS tot_tribute,  " +  // 총 공제액
                        "    (((70000 * ma.work_days) + pt.position_wage + CASE WHEN ma.total_work_hours > (8 * ma.work_days) THEN (10000 * (ma.total_work_hours - 8 * ma.work_days)) ELSE 0 END) - " +
                        "    (((70000 * ma.work_days) + pt.position_wage + CASE WHEN ma.total_work_hours > (8 * ma.work_days) THEN (10000 * (ma.total_work_hours - 8 * ma.work_days)) ELSE 0 END) * (0.033 + 0.0333 + 0.045 + 0.008) + " +
                        "    (((70000 * ma.work_days) + pt.position_wage + CASE WHEN ma.total_work_hours > (8 * ma.work_days) THEN (10000 * (ma.total_work_hours - 8 * ma.work_days)) ELSE 0 END) * 0.033 * 0.1))) AS real_number,  " +  // 실수령액
                        "    LAST_DAY(CONCAT(?, '-', LPAD(?, 2, '0'), '-01')) AS payment_date  " +  // 급여 지급일을 해당 월의 마지막 날로 설정
                        "FROM " +
                        "    temp_monthly_attendance ma " +
                        "JOIN " +
                        "    position_td pt ON ma.position = pt.position_name " +
                        "ORDER BY " +
                        "    ma.employee_id;";

        // 3. 임시 테이블 삭제
        String dropTempTableSql =
                "DROP TEMPORARY TABLE IF EXISTS temp_monthly_attendance;";

        // 트랜잭션 내에서 SQL 실행
        jdbcTemplate.update(createTempTableSql, year, month);
        jdbcTemplate.update(insertSalaryDataSql, year, month);
        jdbcTemplate.update(dropTempTableSql);
    }
}
