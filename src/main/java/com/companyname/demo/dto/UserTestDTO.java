package com.companyname.demo.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTestDTO {
    private Long id;
    private String fullName;
    private Integer age;
    private String address;
}
