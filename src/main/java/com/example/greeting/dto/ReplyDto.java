package com.example.greeting.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReplyDto {

    private int reply_no;
    private int post_no;
    private String  user_id;
    private String comment;
    private String create_date;
    private String update_date;
}
