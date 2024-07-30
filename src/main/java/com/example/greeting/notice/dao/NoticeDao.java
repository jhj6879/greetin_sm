package com.example.greeting.notice.dao;

import com.example.greeting.notice.dto.NoticeDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.dao.DataAccessException;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public class NoticeDao {

    @Select("select * from post order by post_no desc")
    public List<NoticeDto> getNoticeList(@Param("post_no") int post_no) throws DataAccessException {
        return null;
    }
}
