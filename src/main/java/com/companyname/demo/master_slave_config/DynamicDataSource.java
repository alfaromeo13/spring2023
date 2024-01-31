package com.companyname.demo.master_slave_config;

import com.companyname.demo.enums.DataSourceType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Overridden default db to allow support for datasource routing
 */

@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        if (DynamicDataSourceHolder.getDataSource() != null) {
            log.info("Using datasource: {}", DynamicDataSourceHolder.getDataSource());
            return DynamicDataSourceHolder.getDataSource();
        }
        // Default to master if no specific data source is set
        log.info("No datasource set, using MASTER");
        return DataSourceType.MASTER.getType();
    }
}