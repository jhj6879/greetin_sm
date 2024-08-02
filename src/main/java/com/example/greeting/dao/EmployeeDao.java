package com.example.greeting.dao;

import com.example.greeting.dto.EmployeeDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.query.Param;

@Mapper
public interface EmployeeDao {

    @Insert("insert into employee (user_id, user_pw, employee_id, user_name, r_num, tel, address, email, gender, department, position) "
            + "values (#{user_id}, #{user_pw}, #{employee_id}, #{user_name}, #{r_num}, #{tel}, #{address}, #{email}, #{gender}, #{department}, #{position})")
    boolean insertEmployee(EmployeeDto dto) throws DataAccessException;

    @Select("select count(*) from employee where user_id=#{user_id}")
    int checkId(@Param("user_id") String user_id) throws DataAccessException;

//    @Select("select count(*) from employee where userid=#{user_id} and user_pw=#{user_pw}")
//    public boolean checkMember(@Param("user_id") String user_id, @Param("user_pw") String user_pw) throws DataAccessException;

}