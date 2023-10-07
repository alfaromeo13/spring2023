package com.companyname.demo.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.Errors;

@Getter
@AllArgsConstructor
public class ValidationException extends Exception {
    private Errors errors;
}