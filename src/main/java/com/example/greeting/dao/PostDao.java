package com.example.greeting.dao;

import com.example.greeting.dto.PostDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface PostDao {

    @Insert("insert into post(post_no, title, content, user_id, create_date, update_date, hit_cnt)"
            + " values (#{post_no}, #{title}, #{content}, #{user_id}, now(), now(), 0)")
    @Options(useGeneratedKeys = true, keyProperty = "post_no") //자동으로 번호를 생성해주는 데이터를 사용할 때 사용하는 Options
    public void insertPost(PostDto dto) throws DataAccessException;

    @Select("select * from post where post_no=#{post_no}")
    public PostDto selectPostByPostNo(@Param("post_no") int post_no) throws DataAccessException;

    @Select("select * from post where post_no=#{post_no}") //게시글 번호
    PostDto selectPost(@Param("post_no") int post_no) throws DataAccessException;

    // 검색기능 (concat을 안해주면 문자로 인식해 오류가 난다.)
    @Select("SELECT * FROM post WHERE post_no=#{post_no} AND " +
            "(title LIKE CONCAT('%',#{keyword},'%') OR content LIKE CONCAT('%',#{keyword},'%')) " +
            "ORDER BY post_no DESC LIMIT #{offset}, #{cnt}")
    List<PostDto> selectPostListByKeyword(@Param("post_no") int post_no,
                                          @Param("offset") int offset,
                                          @Param("cnt") int cnt,
                                          @Param("keyword") String keyword) throws DataAccessException;

    @Select("SELECT COUNT(*) FROM post WHERE post_no=#{post_no} AND " +
            "(title LIKE CONCAT('%',#{keyword},'%') OR content LIKE CONCAT('%',#{keyword},'%'))")
    int selectPostCntByKeyword(@Param("post_no") int post_no,
                               @Param("keyword") String keyword) throws DataAccessException;

    @Select("SELECT COUNT(*) FROM post WHERE post_no=#{post_no} AND " +
            "title LIKE CONCAT('%', #{keyword}, '%') LIMIT #{searchType}, #{page}")
    int countPosts(@Param("post_no") int post_no, @Param("keyword") String keyword,
                   @Param("searchType") String searchType, @Param("page") int page) throws DataAccessException;
}
