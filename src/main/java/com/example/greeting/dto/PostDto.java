package com.example.greeting.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostDto {

    private int post_no;
    private String  title;
    private String content;
    private String create_date;
    private String update_date;
    private String user_id;
    private int hit_cnt;

}
