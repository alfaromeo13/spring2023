package com.companyname.demo.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DataSourceType {
    MASTER("master"),
    SLAVE("slave");
    private final String type;
}