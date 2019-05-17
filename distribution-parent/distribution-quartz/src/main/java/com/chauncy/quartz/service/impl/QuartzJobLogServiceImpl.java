package com.chauncy.quartz.service.impl;

import com.chauncy.common.util.Convert;
import com.chauncy.data.domain.po.quartz.QuartzJobLogPO;
import com.chauncy.data.mapper.quartz.QuartzJobLogMapper;
import com.chauncy.quartz.service.QuartzJobLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: HUANGWANCHENG
 * @Date: 2019/04/26 18:15
 * @Version 1.0
 */
@Service
@Slf4j
public class QuartzJobLogServiceImpl implements QuartzJobLogService {

    @Autowired
    private QuartzJobLogMapper jobLogMapper;
    /**
     * 获取quartz调度器日志的计划任务
     *
     * @param jobLog
     * @return
     */
    @Override
    public List<QuartzJobLogPO> selectJobLogList(QuartzJobLogPO jobLog) {
        return jobLogMapper.selectJobLogList(jobLog);
    }

    /**
     * 通过调度任务日志ID查询调度信息
     *
     * @param jobLogId
     * @return
     */
    @Override
    public QuartzJobLogPO selectJobLogById(Long jobLogId) {
        return jobLogMapper.selectJobLogById(jobLogId);
    }

    /**
     * 新增任务日志
     *
     * @param jobLog
     */
    @Override
    public void addJobLog(QuartzJobLogPO jobLog) {
        jobLogMapper.insertJobLog(jobLog);
    }

    /**
     * 批量删除调度日志信息
     *
     * @param ids
     * @return
     */
    @Override
    public int deleteJobLogByIds(String ids) {
        return jobLogMapper.deleteJobLogByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除任务日志
     *
     * @param jobId
     * @return
     */
    @Override
    public int deleteJobLogById(Long jobId) {
        return jobLogMapper.deleteJobLogById(jobId);
    }

    /**
     * 清空任务日志
     */
    @Override
    public void cleanJobLog() {
        jobLogMapper.cleanJobLog();
    }
}
