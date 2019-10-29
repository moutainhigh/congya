package com.chauncy.security.exception;

import com.chauncy.common.enums.system.ResultCode;
import lombok.Data;
import org.springframework.security.authentication.InternalAuthenticationServiceException;

/**
 * @Author huangwancheng
 * @create 2019-05-24 22:20
 *
 * 登陆限制异常
 *
 */
@Data
public class LoginFailLimitException extends InternalAuthenticationServiceException {

    private ResultCode resultCode;

    private String msg;

    public LoginFailLimitException(String msg){
        super(msg);
        this.msg = msg;
    }

    public LoginFailLimitException(ResultCode resultCode){
        super(resultCode.getDescription());
        this.msg = resultCode.getDescription();
        this.resultCode = resultCode;
    }

    public LoginFailLimitException(String msg, Throwable t) {
        super(msg, t);
        this.msg = msg;
    }
}
