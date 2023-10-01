package com.companyname.demo.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Integer age;
    private Boolean isActive;
}