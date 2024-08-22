package com.example.greeting.service;

import com.example.greeting.com.Search;
import com.example.greeting.dao.PostDao;
import com.example.greeting.dto.PostDto;
import com.example.greeting.dto.ReplyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostDao postDao;

    public PostDto putPost(PostDto dto) {
        postDao.insertPost(dto);
        return postDao.selectPostByPostNo(dto.getPost_no());
    }

    public PostDto getBoard(int post_no){
        return postDao.selectPost(post_no);
    }

    // 첫 번째 게시물을 반환하는 메서드
    public PostDto getFirstPost() {
        return postDao.selectFirstPost();
    }

    // 페이징 기능
    public List<PostDto> getInoutNotice(Search search){
        search.calcPage(postDao.selectPostCntByKeyword(search.getKeyword()));
        int offset = search.getOffset();
        int cnt = search.getRecordSize();
        String keyword = search.getKeyword();
        return postDao.selectPostListByKeyword(offset, cnt, keyword);
    }

    public List<PostDto> getPostListByKeyword(Search page) {
        // 검색된 전체 게시물 수 계산
        int total = postDao.selectPostCntByKeyword(page.getKeyword());
        page.calcPage(total);  // 페이징 계산

        // 페이징 계산 후 게시물 목록 가져오기
        return postDao.selectPostListByKeyword(page.getOffset(), page.getRecordSize(), page.getKeyword());
    }


    public List<PostDto> getInoutNoticePage() {
        return postDao.selectNotice();
    }

    public int getTotPostCount(String keyword, String searchType, int page) {
        return postDao.countPosts(keyword, searchType, page);
    }

    public PostDto getPost(int post_no) {
        return postDao.selectPostByPostNo(post_no);
    }

    //클릭할 때마다 조회수 업데이트
    public void cntHitCnt(int post_no) {
        postDao.updateHitCnt(post_no);
    }

    public List<ReplyDto> getReply(int post_no){
        return postDao.selectReply(post_no);
    }

    public int getPostNo(int post_no) {
        return postDao.selectPostNo(post_no);
    }

    public void delPost(int post_no) {
        postDao.deletePost(post_no);
    }

    public PostDto editPost(PostDto dto) {
        postDao.updatePost(dto);
        return postDao.selectPostByPostNo(dto.getPost_no());
    }

    public void putReply(ReplyDto reply) {
        postDao.insertReply(reply);
    }

    public List<PostDto> selectRecentPosts() {
        return postDao.selectRecentPosts();
    }

}