package com.companyname.demo.dto;

import lombok.Data;

@Data
public class PostDTO {
    Integer userId;
    Integer id;
    String title;
    String body;
}