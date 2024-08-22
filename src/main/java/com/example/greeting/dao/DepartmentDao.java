package com.example.greeting.dao;

import com.example.greeting.dto.DepartmentDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DepartmentDao {

    // 부서 리스트 조회
    @Select("SELECT * FROM department_td")
    List<DepartmentDto> getDepartmentList();

    // 부서 추가
    @Insert("INSERT INTO department_td (department, department_name) VALUES (#{department}, #{department_name})")
    void addDepartment(DepartmentDto departmentDto);

    // 부서 삭제
    @Delete("DELETE FROM department_td WHERE department = #{department}")
    void deleteDepartment(String department);

}
