package com.chauncy.security.details;

import com.chauncy.common.enums.system.LoginType;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import lombok.Data;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * 登录验证时，附带增加额外的数据，如验证码、用户类型、手机号码
 * @Author zhangrt
 * @Date 2019/7/2 14:38
 **/
@Data
public class MyAuthenticationDetails extends WebAuthenticationDetails {

    //手机号码
    private final String phone;
    //验证码
    private final String verifyCode;
    //登录方式
    private final LoginType loginType;
    //通过第三方登录获得的用户唯一id
    private final String unionId;






    public MyAuthenticationDetails(HttpServletRequest request) {
        super(request);
        phone=request.getParameter("phone");
        verifyCode=request.getParameter("verifyCode");
        /*if (request.getParameter("loginType")==null){
            throw new ServiceException(ResultCode.PARAM_ERROR,"请提供登录类型！");
        }
        else if (LoginType.valueOf(request.getParameter("loginType"))==null){
            throw new ServiceException(ResultCode.PARAM_ERROR,"登录类型错误！");
        }*/
        //登录类型
        loginType=LoginType.valueOf(request.getParameter("loginType"));
        unionId=request.getParameter("unionId");
    }
}
