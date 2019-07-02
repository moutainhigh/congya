package com.chauncy.security.details;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * 该接口用于在Spring Security登录过程中对用户的登录信息的详细信息进行填充，
 * 默认实现是WebAuthenticationDetailsSource，生成上面的默认实现WebAuthenticationDetails。
 * 我们编写类实现AuthenticationDetailsSource，用于生成自定义的MyWebAuthenticationDetails。
 * @Author zhangrt
 * @Date 2019/7/2 14:49
 **/

@Component
public class MyAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {
    @Override
    public WebAuthenticationDetails buildDetails(HttpServletRequest context) {
        return new MyAuthenticationDetails(context);
    }
}
