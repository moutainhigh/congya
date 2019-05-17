package com.chauncy.quartz.service;

import com.chauncy.quartz.domain.po.QuartzJobPO;

import java.util.List;

/**
 * @Author: HUANGWANCHENG
 * @Date: 2019/04/26 18:12
 * @Version 1.0
 *
 * 定时任务调度信息业务接口
 */
public interface QuartzJobService {

    /**
     * 获取quartz调度器的计划任务
     *
     * @param job
     * @return
     */
    List<QuartzJobPO> selectJobList(QuartzJobPO job);

    /**
     * 通过调度任务ID查询调度信息
     *
     * @param jobId
     * @return
     */
    QuartzJobPO selectJobById(Long jobId);

    /**
     * 暂停任务
     *
     * @param job
     * @return
     */
    int pauseJob(QuartzJobPO job);

    /**
     * 恢复任务
     *
     * @param job
     * @return
     */
    int resumeJob(QuartzJobPO job);

    /**
     * 删除任务后，所对应的trigger也将被删除
     *
     * @param job
     * @return
     */
    int deleteJob(QuartzJobPO job);

    /**
     * 批量删除调度信息
     *
     * @param ids
     */
    void deleteJobByIds(String ids);

    /**
     * 任务调度状态修改
     *
     * @param job
     * @return
     */
    int changeStatus(QuartzJobPO job);

    /**
     * 立即运行任务
     *
     * @param job
     * @return
     */
    int run(QuartzJobPO job);

    /**
     * 新增任务表达式
     *
     * @param job
     * @return
     */
    int insertJobCron(QuartzJobPO job);

    /**
     * 更新任务的时间表达式
     *
     * @param job
     * @return
     */
    int updateJobCron(QuartzJobPO job);

    /**
     * 校验cron表达式是否有效
     *
     * @param cronExpression
     * @return
     */
    boolean checkCronExpressionIsValid(String cronExpression);
}
