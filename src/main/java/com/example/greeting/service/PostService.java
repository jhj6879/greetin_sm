package com.example.greeting.service;

import com.example.greeting.com.Search;
import com.example.greeting.dao.PostDao;
import com.example.greeting.dto.PostDto;
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

    // 페이징 기능
    public List<PostDto> getPostListByBoard(int post_no, Search search){
        search.calcPage(postDao.selectPostCntByKeyword(post_no, search.getKeyword()));
        int offset = search.getOffset();
        int cnt = search.getRecordSize();
        String keyword = search.getKeyword();
        return postDao.selectPostListByKeyword(post_no, offset, cnt, keyword);
    }

    public int getTotPostCount(int post_no, String keyword, String searchType, int page) {
        return postDao.countPosts(post_no, keyword, searchType, page);
    }
}
