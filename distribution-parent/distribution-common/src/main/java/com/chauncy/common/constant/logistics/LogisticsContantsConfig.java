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

    //贵司的查询公司编号
    private String customer;

    //行政区域解析 0-关闭 1-开启
    private int resultv2;

    //单号智能识别 0-关闭 1-开启
    private int autoCom;

    //开启国际版 0-关闭 1-开启
    private int interCom;

    //实时订阅查询请求地址
    private String subscribeUrl;

    //实时查询请求地址
    private String synqueryUrl;

    //智能判断请求地址
    private String autoUrl;



//    @Bean
//    public void instanceLogistics(){
//
//        LogisticsContants.setLogisticsKey(key);
//
//        System.out.println(key);
//    }
}
