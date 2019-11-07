package com.chauncy.web.config.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;

/**
 * @Author cheng
 * @create 2019-11-06 09:19
 *
 * httpRequestListener 请求监听器，注册bean
 * 否则在切面注入HttpServletRequest报错
 */
@Configuration
public class RequestListener {

    @Bean
    public RequestContextListener requestContextListener(){
        return new RequestContextListener();
    }
}
