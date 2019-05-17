package com.chauncy.web.config.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 3、动态数据源
 *
 * @Author: HUANGWANCHENG
 * @Date: 2019/04/24 15:08
 * @Version 1.0
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    //    @Nullable
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDateSoureType();
    }

    public DynamicDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources) {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }
}

