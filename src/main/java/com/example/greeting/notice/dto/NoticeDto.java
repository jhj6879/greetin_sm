package com.example.greeting.notice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NoticeDto {

    private int post_no;
    private String title;
    private String content;
    private String create_date;
    private String Update_date;
    private String user_id;
    private int hit_cnt;
}
