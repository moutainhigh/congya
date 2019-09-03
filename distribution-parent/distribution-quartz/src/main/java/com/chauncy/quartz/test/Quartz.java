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

    public String quartzTest(String texts){

        String text = "测试定时任务传参数";
        log.info("测试定时任务传参数");
        log.info(texts);
        return text;
    }


    public String quartzTest2(){

        String text = "无参定时任务测试";
        log.info("无参定时任务测试");
        return text;
    }

}
