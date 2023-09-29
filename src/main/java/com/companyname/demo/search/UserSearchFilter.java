package com.companyname.demo.search;

import lombok.Data;

@Data
public class UserSearchFilter {
    // attribute names must be same as query param names
    // which are passed and spring will map key to its value
    // if key is missing default value is 'null'
    private String name;
    private Integer ageTo;
    private Integer ageFrom;
}
