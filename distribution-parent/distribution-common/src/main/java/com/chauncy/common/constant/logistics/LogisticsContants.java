package com.chauncy.common.constant.logistics;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 系统常量配置
 *
 * @Author: HUANGWANCHENG
 * @Date: 2019/08/01 19:17
 * @Version 1.0
 */
@Component
public class LogisticsContants {

    public static String logisticsKey;

    public static String getLogisticsKey() {
        return logisticsKey;
    }

    public static void setLogisticsKey(String logisticsKey) {
        LogisticsContants.logisticsKey = logisticsKey;
    }

//    @Bean
//    public void a(){
//        System.out.println(getLogisticsKey()+"!!!!!!!!!");
//    }
}
