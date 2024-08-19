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

    // 부서 수정
    @Update("UPDATE department_td SET department_name = #{department_name} WHERE department = #{department}")
    void updateDepartment(DepartmentDto departmentDto);

    // 부서 삭제
    @Delete("DELETE FROM department_td WHERE department = #{department}")
    void deleteDepartment(String department);

    // 부서 코드로 특정 부서 조회
    @Select("SELECT * FROM department_td WHERE department = #{department}")
    DepartmentDto getDepartmentById(String department);

    // 수정할 해당 부서 조회
    @Select("select * from department_td where department = #{department}")
    DepartmentDto selectDepartment();
}
