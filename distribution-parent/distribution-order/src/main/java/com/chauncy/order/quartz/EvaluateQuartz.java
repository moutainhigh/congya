package com.chauncy.order.quartz;

import com.chauncy.order.service.IOmEvaluateQuartzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author cheng
 * @create 2019-09-03 10:53
 *
 * 定时任务计算店铺相关评分
 */
@Component
@Slf4j
public class EvaluateQuartz {

    @Autowired
    private IOmEvaluateQuartzService service;

    /**
     *
     * 定时任务更新店铺相关评分
     *
     */
    public void updateEvaluate(){

        service.updateEvaluate();
    }
}
