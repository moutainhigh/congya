package com.chauncy.common.constant.logistics;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author cheng
 * @create 2019-08-01 17:10
 *
 * 物流相关配置
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "logistics")
public class LogisticsContantsConfig {

    //贵司的授权key
    private String key;

    //回调url
    private String callbackUrl;

//    @Bean
//    public void instanceLogistics(){
//
//        LogisticsContants.setLogisticsKey(key);
//
//        System.out.println(key);
//    }
}
