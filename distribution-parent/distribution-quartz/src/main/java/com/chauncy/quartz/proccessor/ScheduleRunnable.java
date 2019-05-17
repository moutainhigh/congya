package com.chauncy.quartz.proccessor;

import com.chauncy.quartz.util.SpringContextHolder;
import com.chauncy.quartz.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 *
 * @Author: HUANGWANCHENG
 * @Date: 2019/04/26 16:29
 * @Version 1.0
 */
@Slf4j
public class ScheduleRunnable implements Runnable{


    /**
     * 不能直接使用上下文，需要通过实现ApplicationContextAware接口来设置上下文环境
     */
//    @Autowired
//    private SpringContextUtil applicationContext;

    //目标对象
    private Object target;
    //Method对象
    private Method method;
    //参数变量
    private String params;

    public ScheduleRunnable(String name,String methodName,String params) throws NoSuchMethodException {  //执行定时任务传过来的是名称（String类型）

        //通过上下文获取Bean，这里不用根据类名反射获得实例，是因为bean是唯一的，类不一定唯一
        this.target = SpringContextUtil.getBean(name);
        this.params = params;

        if(StringUtils.isNotEmpty(params)){
            this.method = target.getClass().getDeclaredMethod(methodName, String.class);
        }
        else{
            this.method = target.getClass().getDeclaredMethod(methodName);
        }

    }

    //执行定时任务操作
    @Override
    public void run() {
        ReflectionUtils.makeAccessible(method);
        try {
            if (StringUtils.isNotEmpty(params)) {
                method.invoke(target, params);
            }
            else{
                method.invoke(target);
            }
        }catch (Exception e){
            log.error("执行定时任务失败 - : ", e);
        }
    }
}
