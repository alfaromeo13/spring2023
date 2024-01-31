package com.companyname.demo.Interceptors;

import com.companyname.demo.annotations.DataSource;
import com.companyname.demo.master_slave_config.DynamicDataSourceHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Order(1)
@Component
public class DataSourceAspect {
    @Before(value = "@annotation(dataSource)")
    public void dataSourcePoint(JoinPoint joinPoint, DataSource dataSource) {
        log.info("Triggered on -> {} ",dataSource.value());
        DynamicDataSourceHolder.putDataSource(dataSource.value());
        log.info("DataSource set to -> {}",DynamicDataSourceHolder.getDataSource());
    }
}