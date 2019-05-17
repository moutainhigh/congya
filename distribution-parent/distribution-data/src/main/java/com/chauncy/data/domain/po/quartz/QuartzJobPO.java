package com.chauncy.data.domain.po.quartz;

import com.baomidou.mybatisplus.annotation.TableName;
import com.chauncy.common.base.BaseEntity;
import com.chauncy.common.constant.QuartzConstants;
import com.chauncy.common.util.CronUtils;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 定时任务实体类
 *
 * @Author: HUANGWANCHENG
 * @Date: 2019/04/26 17:22
 * @Version 1.0
 */
@TableName("quartz_job")
@Data
@ToString
public class QuartzJobPO extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long jobId;

    private String jobName;

    private String jobGroup;

    private String methodName;

    private String methodParams;

    private String cronExpression;

    //错过的，指本来应该被执行但实际没有被执行的任务调度
    private String misfirePolicy = QuartzConstants.MISFIRE_DEFAULT;

    private String status;

    public Date getNextValidTime() {
        if (StringUtils.isNotEmpty(cronExpression)) {
            return CronUtils.getNextExecution(cronExpression);
        }
        return null;
    }
}
