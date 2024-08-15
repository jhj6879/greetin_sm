package com.example.greeting.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmailDto {

    private String to; //받는 사람 주소
    private String from; //보낸 사람 주소
    private String subject; //제목
    private String text; //내용

}
