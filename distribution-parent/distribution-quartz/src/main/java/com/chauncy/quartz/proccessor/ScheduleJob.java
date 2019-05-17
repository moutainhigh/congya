package com.chauncy.quartz.proccessor;

import com.chauncy.common.constant.Constants;
import com.chauncy.common.constant.QuartzConstants;
import com.chauncy.common.util.BeanUtils;
import com.chauncy.quartz.domain.po.QuartzJobLogPO;
import com.chauncy.quartz.domain.po.QuartzJobPO;
import com.chauncy.quartz.service.QuartzJobLogService;
import com.chauncy.quartz.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *  第三步:创建实现Job接口的被调度的任务ScheduleJob
 *
 *  继承pring框架的QuartzJobBean，重写QuartzJobBean接口中一个方法 execute（JobExecutionContext context）
 *
 *  QuartzJobBean实现了Job接口，Job接口只有一个方法 execute（JobExecutionContext context），在实现接口的 execute 方法中编写所需要定时执行的 Job（任务），
 *  JobExecutionContext 类提供了调度应用的一些信息；Job 运行时的信息保存在 JobDataMap 实例中
 *
 * @Author: HUANGWANCHENG
 * @Date: 2019/04/26 17:08
 * @Version 1.0
 *
 * 定时任务到时间时，将要执行的该job类中的execute方法：
 */
@Slf4j
@DisallowConcurrentExecution
public class ScheduleJob extends QuartzJobBean {

    //创建线程池，单个
    private ExecutorService service = Executors.newSingleThreadExecutor();

//    @Autowired
//    private QuartzJobLogService logService;

    private final static QuartzJobLogService logService = (QuartzJobLogService) SpringContextUtil.getBean("quartzJobLogServiceImpl");

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        QuartzJobPO quartzJob = new QuartzJobPO();
        BeanUtils.copyBeanProp(quartzJob,jobExecutionContext.getMergedJobDataMap().get(QuartzConstants.TASK_PROPERTIES));

        QuartzJobLogPO jobLog = new QuartzJobLogPO();
        jobLog.setJobName(quartzJob.getJobName());
        jobLog.setJobGroup(quartzJob.getJobGroup());
        jobLog.setMethodName(quartzJob.getMethodName());
        jobLog.setMethodParams(quartzJob.getMethodParams());

        jobLog.setCreateTime(new Date());
        long startTime = System.currentTimeMillis();
        try {
            // 执行任务
            log.info("任务开始执行 - 名称：{} 方法：{}", quartzJob.getJobName(), quartzJob.getMethodName());
            ScheduleRunnable task = new ScheduleRunnable(quartzJob.getJobName(), quartzJob.getMethodName(), quartzJob.getMethodParams());
            Future<?> future = service.submit(task);
            future.get();
            long times = System.currentTimeMillis() - startTime;
            // 任务状态 0：成功 1：失败
            jobLog.setStatus(Constants.SUCCESS);
            jobLog.setJobMessage(quartzJob.getJobName() + " 总共耗时：" + times + "毫秒");

            log.info("任务执行结束 - 名称：{} 耗时：{} 毫秒", quartzJob.getJobName(), times);
        } catch (Exception e) {
            log.info("任务执行失败 - 名称：{} 方法：{}", quartzJob.getJobName(), quartzJob.getMethodName());
            log.error("任务执行异常  - ：", e);
            long times = System.currentTimeMillis() - startTime;
            jobLog.setJobMessage(quartzJob.getJobName() + " 总共耗时：" + times + "毫秒");
            // 任务状态 0：成功 1：失败
            jobLog.setStatus(Constants.FAIL);
            jobLog.setExceptionInfo(StringUtils.substring(e.getMessage(), 0, 2000));
        } finally {
            logService.addJobLog(jobLog);
        }
    }
}
