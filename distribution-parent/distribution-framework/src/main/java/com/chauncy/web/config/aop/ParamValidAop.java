package com.chauncy.web.config.aop;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * 参数校验切面
 * @Author zhangrt
 * @Date 2019-06-03 15:02
 **/
@Aspect
@Component
public class ParamValidAop {

    @Before("execution(public * com.chauncy.web..*Api.*(..))")
    public void doBefore(JoinPoint joinPoint) {
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof BindingResult) {
                BindingResult result = (BindingResult) arg;
                if (result.hasErrors()) {
                    FieldError error = result.getFieldError();
                    throw new ServiceException(ResultCode.PARAM_ERROR,error.getField()+":"+error.getDefaultMessage());
                }
            }
        }
    }
}
