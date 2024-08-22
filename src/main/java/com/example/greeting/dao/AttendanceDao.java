package com.example.greeting.dao;

import com.example.greeting.dto.AttendanceDto;
import com.example.greeting.dto.EmployeeDto;
import com.example.greeting.dto.LeaveDto;
import com.example.greeting.dto.PostDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface AttendanceDao {

    // index에서 출근 버튼 눌렀을 때 출근 시간 입력
    @Insert("INSERT INTO attendance " +
            "(employee_id, user_name, department, position, start_time, end_time, over_time, holi_day, user_id, attendance_date) " +
            "SELECT e.employee_id, #{user_name}, #{department}, #{position}, #{start_time}, #{end_time}, #{over_time}, #{holi_day}, #{user_id} , DATE(#{start_time})" +
            "FROM employee e WHERE e.user_id = #{user_id} " +
            "ON DUPLICATE KEY UPDATE " +
            "start_time = VALUES(start_time)")
    void saveOrUpdate(AttendanceDto att) throws DataAccessException;

    // index에서 퇴근 버튼 눌렀을 때 퇴근 시간 입력
    @Update("UPDATE attendance SET " +
            "end_time = #{end_time}, " +
            "over_time = #{over_time}, " +
            "holi_day = #{holi_day} " +
            "WHERE user_id = #{user_id} " +  // employee_id 대신 user_id로 변경
            "AND attendance_date = DATE(#{end_time})")
    void updateAttendance(AttendanceDto att) throws DataAccessException;

    @Select("SELECT * FROM attendance WHERE user_id = #{user_id}")
    AttendanceDto getTime(String user_id) throws DataAccessException;

    // 휴일 신청 직원 정보 조회
    @Select("SELECT e.employee_id, e.user_name, " +
            "CASE " +
            "WHEN e.department = '10' THEN '재정관리팀' " +
            "WHEN e.department = '20' THEN '인사관리팀' " +
            "WHEN e.department = '30' THEN '영업팀' " +
            "WHEN e.department = '40' THEN '품질보증팀' " +
            "ELSE 'Unknown Department' " +
            "END AS department, " +
            "l.holi_day, l.start_day, l.end_day, l.leave_reason " +
            "FROM `leave` l " +
            "JOIN employee e ON l.employee_id = e.employee_id " +
            "WHERE e.employee_id = #{employee_id}")
    LeaveDto selectEmployee(@Param("employee_id") int employee_id) throws DataAccessException;

    // 휴가 신청 입력
    @Insert("INSERT INTO `leave` (leave_id, employee_id, user_name, department, holi_day, start_day, end_day, leave_reason) " +
            "VALUES (#{leave_id}, #{employee_id}, #{user_name}, #{department}, #{holi_day}, #{start_day}, #{end_day}, #{leave_reason})")
    void insertLeave(LeaveDto leaveDto);

    // index에서 최근 휴가계획 5개 조회
    @Select("SELECT * FROM `leave` ORDER BY leave_id DESC LIMIT 5")
    List<LeaveDto> selectRecentPosts() throws DataAccessException;

    // 근태 시간계산해서 조회
    @Select("SELECT " +
            "    employee_id, " +
            "    user_name, " +
            "    attendance_date, " +
            "    MIN(DATE_FORMAT(start_time, '%H:%i')) AS start_time, " +
            "    MAX(DATE_FORMAT(end_time, '%H:%i')) AS end_time, " +
            "    FLOOR(TIMESTAMPDIFF(MINUTE, MIN(start_time), MAX(end_time)) / 60.0) AS working_hours, " +
            "    FLOOR(CASE " +
            "                WHEN MIN(start_time) > STR_TO_DATE(CONCAT(DATE(attendance_date), ' 09:00:00'), '%Y-%m-%d %H:%i:%s') " +
            "                THEN TIMESTAMPDIFF(MINUTE, STR_TO_DATE(CONCAT(DATE(attendance_date), ' 09:00:00'), '%Y-%m-%d %H:%i:%s'), MIN(start_time)) / 60 " +
            "                ELSE 0 " +
            "           END) AS latency_time, " +
            "    FLOOR(CASE " +
            "                WHEN MAX(end_time) < STR_TO_DATE(CONCAT(DATE(attendance_date), ' 18:00:00'), '%Y-%m-%d %H:%i:%s') " +
            "                THEN TIMESTAMPDIFF(MINUTE, MAX(end_time), STR_TO_DATE(CONCAT(DATE(attendance_date), ' 18:00:00'), '%Y-%m-%d %H:%i:%s')) / 60 " +
            "                ELSE 0 " +
            "           END) AS early_leave_time, " +
            "    FLOOR(CASE " +
            "                WHEN MAX(end_time) > STR_TO_DATE(CONCAT(DATE(attendance_date), ' 18:00:00'), '%Y-%m-%d %H:%i:%s') " +
            "                THEN TIMESTAMPDIFF(MINUTE, STR_TO_DATE(CONCAT(DATE(attendance_date), ' 18:00:00'), '%Y-%m-%d %H:%i:%s'), MAX(end_time)) / 60 " +
            "                ELSE 0 " +
            "           END) AS over_time " +
            "FROM attendance " +
            "WHERE DATE_FORMAT(attendance_date, '%Y-%m') = #{yearMonth} " +  // 년/월로 필터링
            "AND (user_name LIKE CONCAT('%', #{userName}, '%') OR #{userName} IS NULL) " +  // 직원 이름으로 필터링, 이름이 없으면 전체 조회
            "GROUP BY employee_id, user_name, attendance_date")
    List<AttendanceDto> getAttendanceByMonthAndName(@Param("yearMonth") String yearMonth, @Param("userName") String userName) throws DataAccessException;

    // 직원별 근무일수를 계산하는 쿼리
    @Select("SELECT COUNT(DISTINCT DATE(start_time)) " +
            "FROM attendance " +
            "WHERE employee_id = #{employee_id} " +
            "AND YEAR(start_time) = #{year} " +
            "AND MONTH(start_time) = #{month}")
    int countWorkDays(@Param("employee_id") int employee_id,
                      @Param("year") int year,
                      @Param("month") int month);
}