package com.example.greeting.dao;

import com.example.greeting.dto.AttendanceDto;
import com.example.greeting.dto.EmployeeDto;
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

    @Select("SELECT employee_id, user_name, " +
            "DATE(start_time) as work_date, " +  // 날짜별로 그룹화하기 위해 날짜만 추출
            "MIN(start_time) as start_time, " +  // 하루의 출근 시간을 가져오기 위해 MIN 사용
            "MAX(end_time) as end_time, " +      // 하루의 퇴근 시간을 가져오기 위해 MAX 사용
            "TIMESTAMPDIFF(MINUTE, MIN(start_time), MAX(end_time)) / 60.0 AS working_hours, " +
            "CASE WHEN MIN(start_time) > '09:00:00' THEN TIMESTAMPDIFF(MINUTE, '09:00:00', MIN(start_time)) / 60.0 ELSE 0 END AS latency_time, " +
            "CASE WHEN MAX(end_time) < '18:00:00' THEN TIMESTAMPDIFF(MINUTE, MAX(end_time), '18:00:00') / 60.0 ELSE 0 END AS early_leave_time, " +
            "CASE WHEN MAX(end_time) > '18:00:00' THEN TIMESTAMPDIFF(MINUTE, '18:00:00', MAX(end_time)) / 60.0 ELSE 0 END AS over_time, " +
            "holi_day " +
            "FROM attendance " +
            "WHERE employee_id = #{employee_id} " +
            "GROUP BY employee_id, user_name, DATE(start_time)")
    List<AttendanceDto> getAttList(@Param("employee_id") int employee_id) throws DataAccessException;

    @Select("SELECT \n" +
            "    employee_id, \n" +
            "    user_name,\n" +
            "    attendance_date, /* 날짜별로 그룹화하기 위해 날짜만 추출 */\n" +
            "    MIN(DATE_FORMAT(start_time, '%H:%i')) AS start_time,\n" +
            "    MAX(DATE_FORMAT(end_time, '%H:%i')) AS end_time,\n" +
            "    FLOOR(TIMESTAMPDIFF(MINUTE, MIN(start_time), MAX(end_time)) / 60.0) AS working_hours,\n" +
            "    FLOOR(CASE \n" +
            "                WHEN MIN(start_time) > STR_TO_DATE(CONCAT(DATE(attendance_date), ' 09:00:00'), '%Y-%m-%d %H:%i:%s') \n" +
            "                THEN TIMESTAMPDIFF(MINUTE, STR_TO_DATE(CONCAT(DATE(attendance_date), ' 09:00:00'), '%Y-%m-%d %H:%i:%s'), MIN(start_time)) / 60 \n" +
            "                ELSE 0 \n" +
            "           END) AS latency_time,\n" +
            "    FLOOR(CASE \n" +
            "                WHEN MAX(end_time) < STR_TO_DATE(CONCAT(DATE(attendance_date), ' 18:00:00'), '%Y-%m-%d %H:%i:%s') \n" +
            "                THEN TIMESTAMPDIFF(MINUTE, MAX(end_time), STR_TO_DATE(CONCAT(DATE(attendance_date), ' 18:00:00'), '%Y-%m-%d %H:%i:%s')) / 60 \n" +
            "                ELSE 0 \n" +
            "           END) AS early_leave_time,\n" +
            "    FLOOR(CASE \n" +
            "                WHEN MAX(end_time) > STR_TO_DATE(CONCAT(DATE(attendance_date), ' 18:00:00'), '%Y-%m-%d %H:%i:%s') \n" +
            "                THEN TIMESTAMPDIFF(MINUTE, STR_TO_DATE(CONCAT(DATE(attendance_date), ' 18:00:00'), '%Y-%m-%d %H:%i:%s'), MAX(end_time)) / 60 \n" +
            "                ELSE 0 \n" +
            "           END) AS over_time,\n" +
            "    holi_day\n" +
            "FROM attendance\n" +
            "GROUP BY employee_id, user_name, attendance_date;\n")
    List<AttendanceDto> getAllAttList() throws DataAccessException;

}
