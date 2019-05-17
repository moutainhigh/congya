package com.chauncy.data.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.chauncy.common.base.BaseEntity;
import lombok.Data;

/**
 * @Author: HUANGWANCHENG
 * @Date: 2019/04/26 17:23
 * @Version 1.0
 */
@TableName("quartz_log_job")
@Data
public class QuartzJobLogPO extends BaseEntity {

    private Long jobLogId;
    private String jobName;
    private String jobGroup;
    private String methodName;
    private String methodParams;
    private String jobMessage;
    private String status;
    private String exceptionInfo;

}
