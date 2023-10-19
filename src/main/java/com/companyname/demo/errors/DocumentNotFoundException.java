package com.companyname.demo.errors;

import lombok.Getter;

@Getter
public class DocumentNotFoundException extends RuntimeException {

    private final String message;

    public DocumentNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}