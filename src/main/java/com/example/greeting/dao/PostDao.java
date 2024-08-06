package com.example.greeting.dao;

import com.example.greeting.dto.PostDto;
import com.example.greeting.dto.ReplyDto;
import org.apache.ibatis.annotations.*;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface PostDao {

    @Insert("insert into post(post_no, title, content, user_id, create_date, update_date, hit_cnt)"
            + " values (#{post_no}, #{title}, #{content}, #{user_id}, now(), now(), 0)")
    @Options(useGeneratedKeys = true, keyProperty = "post_no")
    void insertPost(PostDto dto) throws DataAccessException;

    @Select("select * from post where post_no=#{post_no}")
    PostDto selectPostByPostNo(@Param("post_no") int post_no) throws DataAccessException;

    @Select("select * from post where post_no=#{post_no}")
    PostDto selectPost(@Param("post_no") int post_no) throws DataAccessException;

    @Select("SELECT * FROM post ORDER BY post_no ASC LIMIT 1")
    PostDto selectFirstPost() throws DataAccessException;

    // 검색 기능 (concat을 안해주면 문자로 인식해 오류가 난다.)
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

    /// 페이징을 위한 게시물 수 카운트
    @Select("SELECT COUNT(*) FROM post WHERE post_no=#{post_no} AND " +
            "title LIKE CONCAT('%', #{keyword}, '%')")
    int countPosts(@Param("post_no") int post_no, @Param("keyword") String keyword) throws DataAccessException;

    @Select("select post_no,title,user_id,create_date,hit_cnt FROM post")
    List<PostDto> selectNotice() throws DataAccessException;

    @Update("update post set hit_cnt = hit_cnt + 1 where post_no=#{post_no}")
    void updateHitCnt(@Param("post_no") int post_no) throws DataAccessException;

    @Select("select * from reply where post_no=#{post_no} order by reply_no desc")
    List<ReplyDto> selectReply(@Param ("post_no") int post_no) throws DataAccessException;

    @Select("select * from post where post_no=#{post_no}")
    int selectPostNo(@Param("post_no") int post_no) throws DataAccessException;

    @Delete("delete from post where post_no=#{post_no}")
    void deletePost(@Param ("post_no") int post_no) throws DataAccessException;

    @Update("update post set title=#{title}, content=#{content}, update_date=now() where post_no=#{post_no}")
    void updatePost(PostDto dto) throws DataAccessException;

    @Insert("insert into reply values (#{reply_no}, #{post_no}, #{user_id}, #{comment}, now(), now())")
    void insertReply(ReplyDto reply) throws DataAccessException;

    // 최근 게시글 5개 조회
    @Select("SELECT * FROM post ORDER BY post_no DESC LIMIT 5")
    List<PostDto> selectRecentPosts() throws DataAccessException;
}