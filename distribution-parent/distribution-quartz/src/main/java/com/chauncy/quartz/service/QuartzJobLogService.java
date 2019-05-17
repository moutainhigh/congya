package com.chauncy.quartz.service;

import com.chauncy.quartz.domain.PO.QuartzJobLogPO;

import java.util.List;

/**
 * @Author: HUANGWANCHENG
 * @Date: 2019/04/26 18:12
 * @Version 1.0
 *
 * 定时任务调度日志信息业务接口
 */
public interface QuartzJobLogService {

    /**
     * 获取quartz调度器日志的计划任务
     *
     * @param jobLog
     * @return
     */
    List<QuartzJobLogPO> selectJobLogList(QuartzJobLogPO jobLog);

    /**
     * 通过调度任务日志ID查询调度信息
     *
     * @param jobLogId
     * @return
     */
    QuartzJobLogPO selectJobLogById(Long jobLogId);

    /**
     * 新增任务日志
     *
     * @param jobLog
     */
    void addJobLog(QuartzJobLogPO jobLog);

    /**
     * 批量删除调度日志信息
     *
     * @param ids
     * @return
     */
    int deleteJobLogByIds(String ids);

    /**
     * 删除任务日志
     *
     * @param jobId
     * @return
     */
    int deleteJobLogById(Long jobId);

    /**
     * 清空任务日志
     */
    void cleanJobLog();

}
