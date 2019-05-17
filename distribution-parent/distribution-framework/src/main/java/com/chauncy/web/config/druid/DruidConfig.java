package com.chauncy.web.config.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.chauncy.common.enums.dataSource.DataSourceType;
import com.chauncy.web.config.datasource.DynamicDataSource;
import com.chauncy.web.config.properties.DruidProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 4、druid 连接池配置多数据源
 *
 * @Author: HUANGWANCHENG
 * @Date: 2019/04/24 15:07
 * @Version 1.0
 *
 *  不能访问druid问题未解决>>>>>>>>>>>>>>>>>
 */
@Configuration
public class DruidConfig {

    /**
     * 主库
     *
     * @param druidProperties
     * @return
     */
    @Bean
    @ConfigurationProperties("spring.datasource.druid.master")
    public DataSource masterDataSource(DruidProperties druidProperties) {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return druidProperties.dataSource(dataSource);
    }

    /**
     * 从库
     *
     * @param druidProperties
     * @return
     */
    @Bean
    @ConfigurationProperties("spring.datasource.druid.slave")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave", name = "enabled", havingValue = "true")
    public DataSource slaveDataSource(DruidProperties druidProperties) {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return druidProperties.dataSource(dataSource);
    }

    /**
     * 动态创建数据源
     *
     * @param masterDataSource
     * @param slaveDataSource
     * @return
     */
    @Bean(name = "dynamicDataSource")
    @Primary
    public DynamicDataSource dataSource(DataSource masterDataSource, DataSource slaveDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceType.MASTER.name(), masterDataSource);
        targetDataSources.put(DataSourceType.SLAVE.name(), slaveDataSource);
        return new DynamicDataSource(masterDataSource, targetDataSources);
    }

//    @Bean
//    public ServletRegistrationBean statViewServlet() {
//        // 创建servlet注册实体
//        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),
//                "/druid/*");
//        // 设置ip白名单
//        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
//        // 设置ip黑名单,如果allow与deny共同存在时,deny优先于allow
//        servletRegistrationBean.addInitParameter("deny", "192.168.0.1");
//        // 设置控制台管理用户
//        servletRegistrationBean.addInitParameter("loginUsername", "admin");
//        servletRegistrationBean.addInitParameter("loginPassword", "123456");
//        // 是否可以重置数据
//        servletRegistrationBean.addInitParameter("resetEnable", "false");
//        return servletRegistrationBean;
//    }
//
//    @Bean
//    public FilterRegistrationBean statFilterr() {
//        // 创建过滤器
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
//        // 设置过滤器过滤路径
//        filterRegistrationBean.addUrlPatterns("/*");
//        // 忽略过滤的形式
//        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
//        return filterRegistrationBean;
//    }

}

