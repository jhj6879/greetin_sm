package com.example.greeting.service;

import com.example.greeting.dao.DepartmentDao;
import com.example.greeting.dto.DepartmentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    public List<DepartmentDto> getDepartmentList() {
        return departmentDao.getDepartmentList();
    }

    public void addDepartment(DepartmentDto departmentDto) {
        departmentDao.addDepartment(departmentDto);
    }

    public void updateDepartment(DepartmentDto departmentDto) {
        departmentDao.updateDepartment(departmentDto);
    }

    public void deleteDepartment(String department) {
        departmentDao.deleteDepartment(department);
    }

    // 부서 코드로 특정 부서 조회
    public DepartmentDto getDepartmentById(String department) {
        return departmentDao.getDepartmentById(department);
    }

    public DepartmentDto selectDepartment() {
        return departmentDao.selectDepartment();
    }
}
