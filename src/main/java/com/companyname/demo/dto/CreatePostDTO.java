package com.companyname.demo.dto;

import lombok.Data;

@Data
public class CreatePostDTO {
    Integer userId;
    String title;
    String body;
}
