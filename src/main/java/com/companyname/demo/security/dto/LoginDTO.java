package com.companyname.demo.security.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private String username;
    private String password;
    private boolean rememberMe; //default false...
}