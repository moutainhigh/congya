package com.chauncy.web.config.bean.interceptor;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Author cheng
 * @create 2019-11-20 19:59
 * 从配置文件中读取忽略的url
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "ignored")
public class IgnoredUrlsPropery {

    private List<String> urls;
}

