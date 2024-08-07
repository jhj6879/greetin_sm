package com.example.greeting.dao;

import com.example.greeting.dto.EmployeeDto;
import com.example.greeting.dto.PostDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface EmployeeDao {

    @Insert("insert into employee (user_id, user_pw, employee_id, user_name, r_num, tel, address, email, gender, department, position) "
            + "values (#{user_id}, #{user_pw}, #{employee_id}, #{user_name}, #{r_num}, #{tel}, #{address}, #{email}, #{gender}, #{department}, #{position})")
    boolean insertEmployee(EmployeeDto dto) throws DataAccessException;

    @Select("select count(*) from employee where user_id=#{user_id}")
    int checkId(@Param("user_id") String user_id) throws DataAccessException;

    // 로그인은 security로 해서 위에 수식과 조금 다름 (회원정보 요청할때도 사용함)  // 메서드랑 클래스는 하나에 하나의 기능만
    @Select("select * from employee where user_id=#{user_id}")
    public EmployeeDto getByUserId(@Param("user_id") String user_id) throws DataAccessException;

    @Select("select employee_id, user_name, hire_date from employee ")
    List<EmployeeDto> selectEmployeeList() throws DataAccessException;

    @Select("select employee_id, user_name, r_num, tel, address, email, gender," +
            " department, position, hire_date, resignation_date, employment, permit" +
            " from employee where employee_id = #{employee_id}")
    EmployeeDto selectEmployee(@Param("employee_id") int employee_id) throws DataAccessException;

    @Update("UPDATE employee SET user_name = #{user_name}, r_num = #{r_num}, tel = #{tel}, " +
            "address = #{address}, email = #{email}, gender = #{gender}, department = #{department}, " +
            "position = #{position}, hire_date = #{hire_date}, resignation_date = #{resignation_date}, " +
            "employment = #{employment}, permit = #{permit} WHERE employee_id = #{employee_id}")
    void updateEmployee(EmployeeDto dto);

}
