package com.chauncy.quartz.proccessor;

import com.chauncy.common.constant.QuartzConstants;
import com.chauncy.common.exception.job.TaskException;
import com.chauncy.data.domain.po.QuartzJobPO;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

/**
 * 定时任务工具类
 *
 * Job Details
 *
 * @Author: HUANGWANCHENG
 * @Date: 2019/04/26 19:20
 * @Version 1.0
 */
@Slf4j
public class ScheduleUtils {

    /**
     * 获取触发器key
     *
     * @param jobId
     * @return
     */
    public static TriggerKey getTriggerKey(Long jobId) {
        return TriggerKey.triggerKey(QuartzConstants.TASK_CLASS_NAME + jobId);
    }

    /**
     * 获取jobKey
     *
     * @param jobId
     * @return
     */
    public static JobKey getJobKey(Long jobId) {
        return JobKey.jobKey(QuartzConstants.TASK_CLASS_NAME + jobId);
    }

    /**
     * 获取表达式触发器
     *
     * @param scheduler
     * @param jobId
     * @return
     */
    public static CronTrigger getCronTrigger(Scheduler scheduler, Long jobId) {
        try {
            return (CronTrigger) scheduler.getTrigger(getTriggerKey(jobId));
        } catch (SchedulerException e) {
            log.error("getCronTrigger 异常：", e);
        }
        return null;
    }

    /**
     * 创建定时任务
     *
     * @param scheduler
     * @param job
     */
    public static void createScheduleJob(Scheduler scheduler, QuartzJobPO job) {
        try {

            // 构建job信息,接收一个 Job 实现类,以便运行时通过 newInstance() 的反射机制实例化 Job。
            JobDetail jobDetail = JobBuilder.newJob(ScheduleJob.class).withIdentity(getJobKey(job.getJobId())).build();

            // 基于表达式构建触发器
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
            cronScheduleBuilder = handleCronScheduleMisfirePolicy(job, cronScheduleBuilder);

            // 按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(job.getJobId()))
                    .withSchedule(cronScheduleBuilder).build();

            // 放入参数，运行时的方法可以获取
            jobDetail.getJobDataMap().put(QuartzConstants.TASK_PROPERTIES, job);//?????

            scheduler.scheduleJob(jobDetail, trigger);

            // 暂停任务
            if (job.getStatus().equals(QuartzConstants.Status.PAUSE.getValue())) {
                pauseJob(scheduler, job.getJobId());
            }
        } catch (SchedulerException e) {
            log.error("createScheduleJob 异常：", e);
        } catch (TaskException e) {
            log.error("createScheduleJob 异常：", e);
        }
    }

    /**
     * 更新定时任务
     *
     * @param scheduler
     * @param job
     */
    public static void updateScheduleJob(Scheduler scheduler, QuartzJobPO job) {
        try {
            TriggerKey triggerKey = getTriggerKey(job.getJobId());

            // 表达式调度构建器
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
            cronScheduleBuilder = handleCronScheduleMisfirePolicy(job, cronScheduleBuilder);

            CronTrigger trigger = getCronTrigger(scheduler, job.getJobId());

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();

            // 参数
            trigger.getJobDataMap().put(QuartzConstants.TASK_PROPERTIES, job);

            scheduler.rescheduleJob(triggerKey, trigger);

            // 暂停任务
            if (job.getStatus().equals(QuartzConstants.Status.PAUSE.getValue())) {
                pauseJob(scheduler, job.getJobId());
            }

        } catch (SchedulerException e) {
            log.error("SchedulerException 异常：", e);
        } catch (TaskException e) {
            log.error("SchedulerException 异常：", e);
        }
    }

    /**
     * 立即执行任务
     *
     * @param scheduler
     * @param job
     * @return
     */
    public static int run(Scheduler scheduler, QuartzJobPO job) {
        int rows = 0;
        try {
            // 参数
            JobDataMap dataMap = new JobDataMap();
            dataMap.put(QuartzConstants.TASK_PROPERTIES, job);

            scheduler.triggerJob(getJobKey(job.getJobId()), dataMap);
            rows = 1;
        } catch (SchedulerException e) {
            log.error("run 异常：", e);
        }
        return rows;
    }

    /**
     * 暂停任务
     *
     * @param scheduler
     * @param jobId
     */
    public static void pauseJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.pauseJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            log.error("pauseJob 异常：", e);
        }
    }

    /**
     * 恢复任务
     */
    public static void resumeJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.resumeJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            log.error("resumeJob 异常：", e);
        }
    }

    /**
     * 删除定时任务
     */
    public static void deleteScheduleJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.deleteJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            log.error("deleteScheduleJob 异常：", e);
        }
    }

    public static CronScheduleBuilder handleCronScheduleMisfirePolicy(QuartzJobPO job, CronScheduleBuilder cb)
            throws TaskException {
        switch (job.getMisfirePolicy()) {
            case QuartzConstants.MISFIRE_DEFAULT:
                return cb;
            case QuartzConstants.MISFIRE_IGNORE_MISFIRES:
                return cb.withMisfireHandlingInstructionIgnoreMisfires();
            case QuartzConstants.MISFIRE_FIRE_AND_PROCEED:
                return cb.withMisfireHandlingInstructionFireAndProceed();
            case QuartzConstants.MISFIRE_DO_NOTHING:
                return cb.withMisfireHandlingInstructionDoNothing();
            default:
                throw new TaskException("The task misfire policy '" + job.getMisfirePolicy() + "' cannot be used in cron schedule tasks", TaskException.Code.CONFIG_ERROR);
        }
    }
}
