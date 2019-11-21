package com.chauncy.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 *
 * @Author cheng
 * @create 2019-11-19 20:06
 *
 * 为异步方法添加traceId
 *
 */
@Aspect
@Component
public class LogMdcAspect {
    private static final String UNIQUE_ID = "traceId";

    //("within(com.track.web..*)")
    @Pointcut("@annotation(com.chauncy.common.annotation.logback.LogBack)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MDC.put(UNIQUE_ID, UUID.randomUUID().toString().replace("-",""));
        Object result = point.proceed();// 执行方法
        MDC.remove(UNIQUE_ID);
        return result;
    }
}
