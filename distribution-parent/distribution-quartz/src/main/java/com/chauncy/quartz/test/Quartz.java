package com.chauncy.quartz.test;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Author huangwancheng
 * @create 2019-04-27 20:04
 */
@Slf4j
@Component
@Data
public class Quartz {

    public String texts = "测试spel";

    public String quartzTest(){

        String text = "定时任务测试@@@@@@";
        log.info("定时任务测试@@@@@");
        return text;
    }


    public String quartzTest2(){

        String text = "定时任务测试";
        log.info("定时任务测试");
        return text;
    }

}
