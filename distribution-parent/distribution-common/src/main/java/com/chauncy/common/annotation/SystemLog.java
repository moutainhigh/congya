package com.chauncy.common.annotation;

import com.chauncy.common.enums.system.LogType;

import java.lang.annotation.*;

/**
 * @Author huangwancheng
 * @create 2019-05-25 00:04
 *
 * 系统日志自定义注解
 *
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})//作用于参数或方法上
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLog {

    /**
     * 日志名称default
     * @return
     */
    String description() default "";

    /**
     * 日志类型
     * @return
     */
    LogType type() default LogType.OPERATION;
}
