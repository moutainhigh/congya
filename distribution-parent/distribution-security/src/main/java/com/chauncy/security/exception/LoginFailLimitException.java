package com.chauncy.security.exception;

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


    private String msg;

    public LoginFailLimitException(String msg){
        super(msg);
        this.msg = msg;
    }

    public LoginFailLimitException(String msg, Throwable t) {
        super(msg, t);
        this.msg = msg;
    }
}
