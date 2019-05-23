package com.chauncy.common.exception.sys;

import com.chauncy.common.enums.ResultCode;

/**
 * @author zhangrt
 * @date 2019-05-22
 * @time 15:21
 */
public class ServiceException extends RuntimeException {

    private ResultCode resultCode = ResultCode.SYSTEM_ERROR;

    public ServiceException(ResultCode resultCode, String errorMessage) {
        this(errorMessage);
        this.resultCode = resultCode;
    }

    public ServiceException(ResultCode resultCode, String errorMessageTemplate, Object... args) {
        this(resultCode, String.format(errorMessageTemplate, args));
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable e) {
        super(message, e);
    }


}
