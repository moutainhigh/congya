package com.chauncy.data.valid.constraint;

import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.LoggerUtil;
import com.chauncy.data.valid.annotation.EnumConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author zhangrt
 * @Date 2019-06-03 15:43
 **/
public class EnumValidator implements ConstraintValidator<EnumConstraint, Object> {
    /**
     * 枚举类型
     */
    Class<?> cl;

    @Override
    public void initialize(EnumConstraint constraintAnnotation) {
        cl = constraintAnnotation.target();
    }


    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {


        if (cl.isEnum()) {
            //枚举类验证
            Method method ;
            try {
                method = cl.getMethod("isExist",Object.class);
            } catch (NoSuchMethodException e) {
                LoggerUtil.error(e);
                constraintValidatorContext.disableDefaultConstraintViolation();//禁用默认的message的值
                //重新添加错误提示语句
                constraintValidatorContext
                        .buildConstraintViolationWithTemplate(cl.getSimpleName()+"枚举类型验证出错：枚举类缺少isExist方法").addConstraintViolation();
                return false;
               // throw new ServiceException(ResultCode.SYSTEM_ERROR,"枚举类型验证出错：枚举类缺少isExist方法");
            }
            /**
             * 枚举类没有instance方法
             */
            Object[] enumInstance = cl.getEnumConstants();

            Object isExist = null;
            try {
                //执行isExist方法
                isExist = method.invoke(enumInstance[0], value);
            } catch (IllegalAccessException | InvocationTargetException e) {
                LoggerUtil.error(e);
                constraintValidatorContext.disableDefaultConstraintViolation();//禁用默认的message的值
                //重新添加错误提示语句
                constraintValidatorContext
                        .buildConstraintViolationWithTemplate(cl.getSimpleName()+"枚举类型验证出错：枚举类缺少isExist方法").addConstraintViolation();
                return false;
                //throw new ServiceException(ResultCode.SYSTEM_ERROR,"枚举类型验证出错：isExists方法调用出错");
            }
            return (Boolean) isExist;
        }
        else {
            constraintValidatorContext.disableDefaultConstraintViolation();//禁用默认的message的值
            //重新添加错误提示语句
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate(cl.getSimpleName()+"枚举类型验证出错：枚举类缺少isExist方法").addConstraintViolation();
            return false;
        }
    }
}
