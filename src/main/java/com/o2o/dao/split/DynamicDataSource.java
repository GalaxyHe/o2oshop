package com.o2o.dao.split;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author He
 * @Date 2019/8/6
 * @Time 18:23
 * @Description TODO
 **/

public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceHolder.getDbType();
    }
}
