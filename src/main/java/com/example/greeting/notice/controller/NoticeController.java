package com.example.greeting.notice.controller;

import com.example.greeting.notice.dto.NoticeDto;
import com.example.greeting.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class NoticeController {

    @Autowired
    private NoticeService service;

    @GetMapping("/notice")
    public String Notice(Model model, @PathVariable("post_no") int post_no){
        List<NoticeDto> list = service.getNoticeList(post_no);
        model.addAttribute("list", list);
        return "notice";
    }

    @GetMapping("/write")
    public String Wriet(){
        return "/write";
    }
}
