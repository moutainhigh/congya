package com.chauncy.quartz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chauncy.quartz.domain.PO.QuartzJobLogPO;

import java.util.List;

/**
 * @Author: HUANGWANCHENG
 * @Date: 2019/04/26 17:54
 * @Version 1.0
 */
public interface QuartzJobLogMapper extends BaseMapper<QuartzJobLogPO> {

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
     * @return
     */
    int insertJobLog(QuartzJobLogPO jobLog);

    /**
     * 批量删除调度日志信息
     *
     * @param ids
     * @return
     */
    int deleteJobLogByIds(String[] ids);

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
