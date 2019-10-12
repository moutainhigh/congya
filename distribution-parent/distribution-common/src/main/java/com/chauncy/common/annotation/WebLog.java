package com.chauncy.common.annotation;

import java.lang.annotation.*;

/**
 * @author yeJH
 * @since 2019/10/12 12:16
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface WebLog {
    /**
     * 日志描述信息
     *
     * @return
     */
    String description() default "";

}