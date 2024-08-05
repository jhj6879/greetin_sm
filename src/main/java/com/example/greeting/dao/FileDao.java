package com.example.greeting.dao;

import com.example.greeting.dto.FileDto;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface FileDao {

    // 파일 업로드
    @Insert("insert into filemng values(#{file_id}, #{file_name}, #{file_path}, #{org_file_name}, #{userid}, #{post_no})")
    public void insertFile(FileDto file) throws DataAccessException;

    // 파일 리스트 조회
    @Select("select * from filemng where post_no=#{post_no}")
    public List<FileDto> selectFileByPostNo(@Param("post_no") int psot_no) throws DataAccessException;

    // 파일 건당 조회
    @Select("select * from filemng where file_id=#{file_id}")
    public FileDto selectFileById(@Param("file_id") int file_id) throws DataAccessException;

    // 파일 삭제
    @Delete("delete from filemng where file_id=#{file_id}")
    public void deleteFileById(@Param("file_id") int file_id) throws DataAccessException;


}
