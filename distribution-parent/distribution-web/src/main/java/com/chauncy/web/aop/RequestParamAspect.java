package com.chauncy.web.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.chauncy.common.util.JSONUtils;
import com.chauncy.common.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author cheng
 * @create 2019-11-07 00:10
 */
@Aspect
@Component
@Slf4j
public class RequestParamAspect {

    @Autowired(required = false)
    private HttpServletRequest request;

    /**
     * controller层切点
     */
    @Pointcut("within(com.chauncy.web..*Api)")
    public void requestParamAspect() {

    }

    @Around("requestParamAspect()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws InterruptedException {

        log.info(String.format("%%%%%%%%%%%%%%%%%%%%请求开始，请求路径为:【%s】",request.getRequestURI()));
        Object rvt = null;

        try {
            String requestParam = "";
            //获取json参数
            Object[] args = joinPoint.getArgs();
            //请求参数Json/form
            //json方式
            if (request.getContentType() != null) {
                if (request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE) ||
                        request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
                    //登录处理,由于在provider获取额外数据获取了流，故在这里获取不到请求参数，可以通过details获取，但为了不依赖security,置空先
                    if (joinPoint.getSignature().getName().equals("onAuthenticationSuccess")) {
                        requestParam = "忽略";
                    } else {
                        //param
                        Object object = args[0];
                        Map<String, Object> jsonParam = JSONUtils.toHashMap(object);
                        requestParam = JSON.toJSONStringWithDateFormat(jsonParam, "yyyy-MM-dd HH:mm:ss", SerializerFeature.UseSingleQuotes);

                    }
                    log.info(String.format("方法【%s】请求参数为:【%s】", joinPoint.getTarget().toString() + "----" + joinPoint.getSignature().getName(), requestParam));
                }
                //form表单
                else {
                    Map<String, String[]> logParams = request.getParameterMap();
                    requestParam = request.getQueryString();
                    log.info(String.format("方法【%s】请求参数为:【%s】", joinPoint.getTarget().toString() + "----" + joinPoint.getSignature().getName(), ObjectUtil.mapToString(logParams)));
                }
            }else {
                log.info(String.format("方法【%s】请求参数为:【无参请求】", joinPoint.getTarget().toString() + "----" + joinPoint.getSignature().getName()));
            }

            //执行目标方法
            rvt = joinPoint.proceed(args);
            log.info(String.format("%%%%%%%%%%%%%%%%%%%%请求结束，请求路径为:【%s】",request.getRequestURI()));

        }catch (Exception e){
            log.error("AOP环绕通知异常",e);
        } catch (Throwable throwable) {
            log.error("AOP环绕通知执行方法异常",throwable);
        }

        return rvt;
    }
}
