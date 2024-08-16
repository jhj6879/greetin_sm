package com.example.greeting.service;

import com.example.greeting.com.Search;
import com.example.greeting.dao.EmployeeDao;
import com.example.greeting.dto.EmployeeDto;
import com.example.greeting.dto.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeDao employeeDao;
    private final PasswordEncoder passwordEncoder;

    public EmployeeDto create(EmployeeDto dto) {
        dto.setUser_pw(passwordEncoder.encode(dto.getUser_pw()));
        this.employeeDao.insertEmployee(dto);
        return dto;
    }

    public boolean memberId(String user_id) {
        boolean result = false;
        if(employeeDao.checkId(user_id) == 0) {
            result = true;
        }
        return result;
    }

    public EmployeeDto getMemberInfo(String userid) {
        EmployeeDto dto = new EmployeeDto();
        dto = employeeDao.getByUserId(userid);
        return dto;
    }

    public List<EmployeeDto> selectEmployeeList() {
        return employeeDao.selectEmployeeList();
    }

    public List<EmployeeDto> getPostListByKeyword(Search page) {
        // 검색된 전체 게시물 수 계산
        int total = employeeDao.selectPostCntByKeyword(page.getKeyword());
        page.calcPage(total);  // 페이징 계산

        // 페이징 계산 후 게시물 목록 가져오기
        return employeeDao.selectPostListByKeyword(page.getOffset(), page.getRecordSize(), page.getKeyword());
    }


    public List<EmployeeDto> getInoutNoticePage() {
        return employeeDao.selectNotice();
    }

    public EmployeeDto selectEmployee(int employee_id) {
        return employeeDao.selectEmployee(employee_id);
    }

    public void updateEmployee(EmployeeDto dto) {
        if (dto.getHire_date() != null && dto.getHire_date().trim().isEmpty()) {
            dto.setHire_date(null);
        }
        if (dto.getResignation_date() != null && dto.getResignation_date().trim().isEmpty()) {
            dto.setResignation_date(null);
        }
        employeeDao.updateEmployee(dto);
    }

    public EmployeeDto getEmployeeById(String userId) {
//        String user_id = employeeDao.findUserIdByUserName(userName);
//        System.out.println("Found userId: " + user_id + " for userName: " + user_name);
        return employeeDao.findEmployeeById(userId);
    }

    public EmployeeDto getEmployeeId(String userId) {
        EmployeeDto employeeDto = employeeDao.findEmployeeId(userId);
        if (employeeDto == null) {
            System.out.println("No employee found with userId: " + userId);
        } else {
            System.out.println("Employee found: " + employeeDto);
        }
        return employeeDto;
    }

}