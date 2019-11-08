package com.chauncy.quartz.manage;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author yeJH
 * @Description 账单定时任务  店铺设置结算周期  周为单位  每周一生成上周的账单
 * @since 2019/11/5 22:25
 */
@Slf4j
@Component
@Data
public class BillQuartz {

    public void c(String texts){

        String text = "测试定时任务传参数";
        log.info("测试定时任务传参数");
        log.info(texts);
    }

}
