package com.chauncy.web.config;

import com.chauncy.data.response.entity.JsonViewData;
import com.chauncy.data.response.entity.ResultCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author: zhangrt
 * createAt: 2018/9/21
 */
@Component("RestAuthenticationAccessDeniedHandler")
public class RestAuthenticationAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        //登陆状态下，权限不足执行该方法
        System.out.println("权限不足：" + e.getMessage());
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        JsonViewData jsonViewData=new JsonViewData(ResultCode.NO_AUTH);
        jsonViewData.write(response);
       /* PrintWriter printWriter = response.getWriter();
        String body = ResultJson.failure(ResultCode.FORBIDDEN, e.getMessage()).toString();
        printWriter.write(body);
        printWriter.flush();*/

    }
}
