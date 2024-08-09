package com.example.greeting.service;

import com.example.greeting.dao.AttendanceDao;
import com.example.greeting.dto.AttendanceDto;
import com.example.greeting.dto.EmployeeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

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

}
