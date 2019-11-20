package com.chauncy.common.annotation.limit;

import java.lang.annotation.*;

/**
 * @Author cheng
 * @create 2019-11-20 19:58
 *
 * 限流注解
 */
@Target(ElementType.METHOD)//作用于方法上
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {

    //是否启用ip限流，false--全局限流  true--Ip限流
    boolean isIpLimiter() default true;

    //请求个数
    int limit() default 5;

    //时间（1秒内）
    int timeout() default 1000;
}
