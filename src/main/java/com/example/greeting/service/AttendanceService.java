package com.example.greeting.service;

import com.example.greeting.dao.AttendanceDao;
import com.example.greeting.dto.AttendanceDto;
import com.example.greeting.dto.EmployeeDto;
import com.example.greeting.dto.LeaveDto;
import com.example.greeting.dto.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceDao attendanceDao;

    public AttendanceDto getTime(String user_id) {
        return attendanceDao.getTime(user_id);
    }

    public List<AttendanceDto> getAttendanceList(int employee_id) {
        return attendanceDao.getAttList(employee_id);
    }

    public List<AttendanceDto> getAllAttendanceList() {
        return attendanceDao.getAllAttList();
    }

    // 출근 처리
    public void save(AttendanceDto att) {
        attendanceDao.saveOrUpdate(att);
    }

    // 퇴근 및 휴일 처리
    public void recordClockOutOrHoliday(AttendanceDto att) throws DataAccessException {
        attendanceDao.updateAttendance(att);
    }

    //휴일 관리
    public void applyLeave(LeaveDto leaveDto) {
        // 시작일자와 종료일자를 LocalDate로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(leaveDto.getStart_day(), formatter);
        LocalDate endDate = LocalDate.parse(leaveDto.getEnd_day(), formatter);

        // 기간 계산
        long daysBetween = Duration.between(startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay()).toDays();

        // 연차휴가 또는 반차휴가에 따라 휴가일 계산
        double totalDays = "1".equals(leaveDto.getHoli_day()) ? daysBetween : daysBetween * 0.5;
        leaveDto.setHoli_day(String.valueOf(totalDays));

        // 데이터베이스에 저장
        attendanceDao.insertLeave(leaveDto);
    }

    public LeaveDto selectEmployee(int employee_id) {
        return attendanceDao.selectEmployee(employee_id);
    }

    public List<LeaveDto> selectRecentPosts() {
        return attendanceDao.selectRecentPosts();
    }
}
