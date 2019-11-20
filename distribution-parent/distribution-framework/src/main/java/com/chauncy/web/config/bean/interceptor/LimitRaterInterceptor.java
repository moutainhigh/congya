package com.chauncy.web.config.bean.interceptor;

import com.chauncy.common.annotation.limit.RateLimiter;
import com.chauncy.common.constant.Constants;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.security.util.IpInfoUtil;
import com.chauncy.web.config.bean.limit.RedisRaterLimiter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Author cheng
 * @create 2019-11-20 20:00
 * 自定义限流拦截器
 * 需要继承WebMvcConfigurationSupport 注册该拦截器才能生效
 * 本项目拦截：security MyAuthenticationFilter==>LimitRaterInterceptor==>controller
 *
 */
@Slf4j
@Component
public class LimitRaterInterceptor extends HandlerInterceptorAdapter {

    @Value("${distribution.rateLimit.enable}")
    private boolean rateLimitEnable;

    @Value("${distribution.rateLimit.limit}")
    private Integer limit;

    @Value("${distribution.rateLimit.timeout}")
    private Integer timeout;

    @Value("${distribution.ipRateLimit.enable}")
    private boolean ipRateLimitEnable;

    @Value("${distribution.ipRateLimit.limit}")
    private Integer ipRateLimit;

    @Value("${distribution.ipRateLimit.timeout}")
    private Integer ipRateLimitTimeout;

    @Autowired
    private RedisRaterLimiter redisRaterLimiter;

    @Autowired
    private IpInfoUtil ipInfoUtil;

    /**
     * 预处理回调方法，实现处理器的预处理（如登录检查）
     * 第三个参数为响应的处理器，即controller
     * 返回true，表示继续流程，调用下一个拦截器或者处理器
     * 返回false，表示流程中断，通过response产生响应
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        // IP限流 1秒限1个请求(一般不使用这个进行全局ip限流，使用注解)
        if (ipRateLimitEnable) {
            String token1 = redisRaterLimiter.acquireTokenFromBucket(ipInfoUtil.getIpAddr(request), ipRateLimit, ipRateLimitTimeout);
            if (StringUtils.isBlank(token1)) {
                throw new ServiceException("你手速太快了，请点慢一点");
            }
        }

        //全局限流(所有接口请求)
        if(rateLimitEnable){
            String token2 = redisRaterLimiter.acquireTokenFromBucket(Constants.LIMIT_ALL, limit, timeout);
            if (StringUtils.isBlank(token2)) {
                throw new ServiceException("当前访问总人数太多啦，请稍后再试");
            }
        }

        //注解限流,注意不要try-catch掉
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        RateLimiter rateLimiter = method.getAnnotation(RateLimiter.class);
        if (rateLimiter != null) {
            int limit = rateLimiter.limit();
            int timeout = rateLimiter.timeout();
            boolean isIpLimit = rateLimiter.isIpLimiter();
            if (isIpLimit){
                String token3 = redisRaterLimiter.acquireTokenFromBucket(ipInfoUtil.getIpAddr(request), limit, timeout);
                if (StringUtils.isBlank(token3)) {
                    throw new ServiceException("你手速太快了，请点慢一点");
                }
            }else {
                String token3 = redisRaterLimiter.acquireTokenFromBucket(method.getName(), limit, timeout);
                if (StringUtils.isBlank(token3)) {
                    throw new ServiceException("当前访问人数太多啦，请稍后再试");
                }
            }
        }

        return true;
    }

    /**
     * 当前请求进行处理之后，也就是Controller方法调用之后执行，
     * 但是它会在DispatcherServlet 进行视图返回渲染之前被调用。
     * 此时我们可以通过modelAndView对模型数据进行处理或对视图进行处理。
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 方法将在整个请求结束之后，也就是在DispatcherServlet渲染了对应的视图之后执行。
     * 这个方法的主要作用是用于进行资源清理工作的。
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
    }

}
