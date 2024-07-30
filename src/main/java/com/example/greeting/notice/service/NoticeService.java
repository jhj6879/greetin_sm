package com.example.greeting.notice.service;

import com.example.greeting.notice.dao.NoticeDao;
import com.example.greeting.notice.dto.NoticeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {

    @Autowired
    private NoticeDao dao;

    public List<NoticeDto> getNoticeList(int post_no) {
        return dao.getNoticeList(post_no);
    }
}
