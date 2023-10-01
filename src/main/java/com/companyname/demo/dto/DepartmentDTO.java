package com.companyname.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class DepartmentDTO {
    private Integer id;
    private String name;
    private String description;
    private Boolean isActive;
    private List<UserDTO> users;
}
