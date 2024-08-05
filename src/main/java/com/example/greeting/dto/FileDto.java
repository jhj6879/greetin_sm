package com.example.greeting.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FileDto {

    private int file_id;
    private String file_name;
    private String file_path;
    private String org_file_name;
    private String userid;
    private int post_no;
}
