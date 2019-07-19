package com.chauncy.web.config.handler;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.LoggerUtil;
import com.chauncy.data.vo.JsonViewData;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ValidationException;

/**
 * 统一异常拦截处理
 * @Author zhangrt
 * @Date 2019-05-22 14:08
 **/
@ControllerAdvice
public class MyExceptionHandler {

    /**
     * 处理所有自定义异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public JsonViewData handleServiceException(Exception e){
        LoggerUtil.error(e);
        if(e instanceof ServiceException){
            ServiceException serviceException = (ServiceException) e;
            return new JsonViewData(serviceException.getResultCode(),serviceException.getLocalizedMessage(),serviceException.getData());
        }else if (e instanceof MethodArgumentNotValidException){
            FieldError fieldError = ((MethodArgumentNotValidException) e).getBindingResult().getFieldError();
            Object errorMessage= fieldError.getDefaultMessage();
            String field=fieldError.getField();
            Object value=fieldError.getRejectedValue();
            return new JsonViewData(ResultCode.PARAM_ERROR,String.format("【%s】为【%s】:%s",field,value,errorMessage));
            }
        return new JsonViewData(ResultCode.SYSTEM_ERROR,e.getMessage());
    }
}
