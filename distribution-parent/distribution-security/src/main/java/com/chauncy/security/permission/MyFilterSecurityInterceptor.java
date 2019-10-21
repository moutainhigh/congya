package com.chauncy.security.permission;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * 监控用户行为
 * 权限管理过滤器
 *
 * @Author huangwancheng
 * @create 2019-05-25 00:00
 */
@Slf4j
@Component
public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

    @Autowired
    private FilterInvocationSecurityMetadataSource securityMetadataSource;

    @Autowired
    public void setMyAccessDecisionManager(MyAccessDecisionManager myAccessDecisionManager) {
        super.setAccessDecisionManager(myAccessDecisionManager);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        FilterInvocation fi = new FilterInvocation(request, response, chain);
        invoke(fi);
    }

    public void invoke(FilterInvocation fi) throws IOException, ServletException {

        //默认实现类AbstractSecurityInterceptor的beforeInvocation()方法，通过
        // SecurityMetadataSource obtainSecurityMetadataSource()
        // 1、Collection<ConfigAttribute> attributes = this.obtainSecurityMetadataSource().getAttributes(object);
        // 获取到属性url的值
        // private AccessDecisionManager accessDecisionManager;
        // 2、将该值作为decide(Authentication authentication, Object o, Collection<ConfigAttribute> configAttributes)
        // accessDecisionManager.decide(authenticated, object, attributes)的第三个参数传给AccessDecisionManager

        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            //doFilter()方法就是调用我们的接口处理响应求
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    /**
     * @Author chauncy
     * @Date 2019-10-18 23:27
     * @Description //安全元数据资源--Collection<ConfigAttribute> attributes = this.obtainSecurityMetadataSource()
     * 				.getAttributes(object);
     * 			如果url在权限表中，则返回给decide方法，用来判定用户是否有此权限
     *
     * @Update chauncy
     *
     * @param
     * @return org.springframework.security.access.SecurityMetadataSource
     **/
    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }
}
