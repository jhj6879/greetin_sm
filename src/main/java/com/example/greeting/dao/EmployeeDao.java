package com.example.greeting.dao;

import com.example.greeting.dto.EmployeeDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

@Mapper
public interface EmployeeDao {

    @Insert("insert into employee (user_id, user_pw, employee_id, user_name, r_num, tel, address, email, gender, department, position, hire_date, resignation_date, employment, permit) "
            + "values (#{user_id}, #{user_pw}, #{employee_id}, #{user_name}, #{r_num}, #{tel}, #{address}, #{email}, #{gender}, #{department}, #{position}, null, null, null, null)")
    public boolean insertEmployee(EmployeeDto dto) throws DataAccessException;
}
