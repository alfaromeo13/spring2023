package com.companyname.demo.errors.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDTO {
    private String fieldName;
    private Object rejectedValue;
    private String message; //message from validation annotation constructor
}