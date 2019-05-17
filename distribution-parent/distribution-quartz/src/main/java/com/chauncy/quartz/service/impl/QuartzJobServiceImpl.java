package com.chauncy.quartz.service.impl;

import com.chauncy.common.constant.QuartzConstants;
import com.chauncy.common.util.Convert;
import com.chauncy.quartz.domain.po.QuartzJobPO;
import com.chauncy.quartz.mapper.QuartzJobMapper;
import com.chauncy.quartz.proccessor.ScheduleUtils;
import com.chauncy.quartz.service.QuartzJobService;
import com.chauncy.quartz.util.CronUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 定时任务调度信息业务实现
 *
 * @Author: HUANGWANCHENG
 * @Date: 2019/04/26 18:15
 * @Version 1.0
 */
@Service
@Slf4j
public class QuartzJobServiceImpl implements QuartzJobService {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private QuartzJobMapper jobMapper;

    /**
     * 项目启动时初始化定时器
     */
    @PostConstruct
    public void init() {
        log.info("初始化Begin ： QuartzInit");
        //查询所有的任务job
        List<QuartzJobPO> jobList = jobMapper.selectJobAll();
        for (QuartzJobPO job : jobList) {
            //获取触发器Trigger表达式
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, job.getJobId());
            // 如果不存在，则创建
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, job);
            } else {
                ScheduleUtils.updateScheduleJob(scheduler, job);
            }
        }
        log.info("初始化End ： QuartzInit");
    }

    /**
     * 获取quartz调度器的计划任务列表
     *
     * @param job
     * @return
     */
    @Override
    public List<QuartzJobPO> selectJobList(QuartzJobPO job) {
        return jobMapper.selectJobList(job);
    }

    /**
     * 通过调度任务ID查询调度信息
     *
     * @param jobId
     * @return
     */
    @Override
    public QuartzJobPO selectJobById(Long jobId) {
        return jobMapper.selectJobById(jobId);
    }

    /**
     * 暂停任务
     *
     * @param job
     * @return
     */

    @Override
    public int pauseJob(QuartzJobPO job) {
        job.setStatus(QuartzConstants.Status.PAUSE.getValue());
        int rows = jobMapper.updateJob(job);
        if (rows > 0) {
            ScheduleUtils.pauseJob(scheduler, job.getJobId());
        }
        return rows;
    }

    /**
     * 恢复任务
     *
     * @param job
     * @return
     */
    @Override
    public int resumeJob(QuartzJobPO job) {
        job.setStatus(QuartzConstants.Status.NORMAL.getValue());
        int rows = jobMapper.updateJob(job);
        if (rows > 0) {
            ScheduleUtils.pauseJob(scheduler, job.getJobId());
        }
        return rows;
    }

    /**
     * 删除任务后，所对应的trigger也将被删除
     *
     * @param job
     * @return
     */
    @Override
    public int deleteJob(QuartzJobPO job) {
        int rows = jobMapper.deleteJobById(job.getJobId());
        if (rows > 0) {
            ScheduleUtils.deleteScheduleJob(scheduler, job.getJobId());
        }
        return rows;
    }

    /**
     * 批量删除调度信息
     *
     * @param ids
     */
    @Override
    public void deleteJobByIds(String ids) {
        Long[] jobIds = Convert.toLongArray(ids);
        for (Long jobId : jobIds) {
            QuartzJobPO job = jobMapper.selectJobById(jobId);
            deleteJob(job);
        }
    }

    /**
     * 任务调度状态修改
     *
     * @param job
     * @return
     */
    @Override
    public int changeStatus(QuartzJobPO job) {
        int rows = 0;
        String status = job.getStatus();
        if (QuartzConstants.Status.NORMAL.getValue().equals(status)) {
            rows = resumeJob(job);
        } else if (QuartzConstants.Status.PAUSE.getValue().equals(status)) {
            rows = pauseJob(job);
        }
        return rows;
    }

    /**
     * 立即运行任务
     *
     * @param job
     * @return
     */
    @Override
    public int run(QuartzJobPO job) {
        return ScheduleUtils.run(scheduler,/*job*/selectJobById(job.getJobId()));
    }

    /**
     * 新增任务
     *
     * @param job
     * @return
     */
    @Override
    public int insertJobCron(QuartzJobPO job) {
        job.setStatus(QuartzConstants.Status.PAUSE.getValue());
        int rows = jobMapper.insertJob(job);
        if(rows>0){
            ScheduleUtils.createScheduleJob(scheduler,job);
        }
        return rows;
    }

    /**
     * 更新任务的时间表达式
     *
     * @param job
     * @return
     */
    @Override
    public int updateJobCron(QuartzJobPO job) {
        int rows = jobMapper.updateJob(job);
        if (rows>0){
            ScheduleUtils.updateScheduleJob(scheduler,job);
        }
        return rows;
    }

    /**
     * 校验cron表达式是否有效
     *
     * @param cronExpression
     * @return
     */
    @Override
    public boolean checkCronExpressionIsValid(String cronExpression) {
        return CronUtils.isValid(cronExpression);
    }
}
