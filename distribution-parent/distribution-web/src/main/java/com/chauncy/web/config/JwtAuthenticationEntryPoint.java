package com.chauncy.web.config;

import com.chauncy.data.response.entity.JsonViewData;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * @author: zhangrt
 * createAt: 2018/9/20
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        //验证为未登陆状态会进入此方法，认证错误
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        //PrintWriter printWriter = response.getWriter();
        JsonViewData.NO_LOGIN_VIEW.write(response);
        //String body = ResultJson.failure(ResultCode.UNAUTHORIZED, authException.getMessage()).toString();
       /* String body = "验证失败";
        printWriter.write(body);
        printWriter.flush();*/
    }
}