package com.companyname.demo.annotations;

import com.companyname.demo.enums.DataSourceType;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface DataSource {
    DataSourceType value() default DataSourceType.MASTER;
}
//with @DataSource annotation we specify whether a method should use the master or slave datasource.