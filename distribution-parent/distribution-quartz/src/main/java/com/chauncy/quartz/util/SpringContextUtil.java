package com.chauncy.quartz.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 *
 * @Author: HUANGWANCHENG
 * @Date: 2019/04/26 16:17
 * @Version 1.0
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    //Spring应用上下文环境
    private static ApplicationContext applicationContext;

    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     *
     * @param applicationContext
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     *
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext(){

        return applicationContext;
    }

    /**
     * 获取对象
     *
     * @param name
     * @return
     * @throws BeansException
     */
    public static Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }

}
