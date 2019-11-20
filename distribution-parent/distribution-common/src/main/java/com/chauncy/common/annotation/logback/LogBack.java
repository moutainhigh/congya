package com.chauncy.common.annotation.logback;

import java.lang.annotation.*;

/**
 * @Author cheng
 * @create 2019-11-19 21:26
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})//作用于参数或方法上
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogBack {
}
