package com.chauncy.common.exception.sys;

import com.chauncy.common.enums.system.ResultCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.security.sasl.AuthenticationException;

/**
 * @author zhangrt
 * @date 2019-05-22
 * @time 15:21
 */
@Data
public class ServiceException extends RuntimeException {

    private ResultCode resultCode = ResultCode.SYSTEM_ERROR;

    private Object data;

    public ServiceException(ResultCode resultCode) {
        this(resultCode.getDescription());
        this.resultCode = resultCode;
    }

    public ServiceException(ResultCode resultCode, String errorMessage) {
        this(errorMessage);
        this.resultCode = resultCode;
    }

    public ServiceException(ResultCode resultCode, String errorMessage,Object data) {
        this(errorMessage);
        this.resultCode = resultCode;
        this.data=data;
    }


    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable e) {
        super(message, e);
    }


}
