package com.chauncy.common.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Iterator;
import java.util.Set;

/**
 * 手动校验参数
 * @Author zhangrt
 * @Date 2019/6/19 11:15
 **/
public class MyValidUtil {

    public static String check(Object obj) {
        if (null == obj) {
            return "入参不能为空";
        }
        Set<ConstraintViolation<Object>> validResult = Validation.buildDefaultValidatorFactory().getValidator().validate(obj);
        if (null != validResult && validResult.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (Iterator<ConstraintViolation<Object>> iterator = validResult.iterator(); iterator.hasNext();) {
                ConstraintViolation<Object> constraintViolation = (ConstraintViolation<Object>) iterator.next();
                if(StringUtils.isNotBlank(constraintViolation.getMessage())) {
                    sb.append(constraintViolation.getMessage()).append("、");
                } else {
                    sb.append(constraintViolation.getPropertyPath().toString()).append("数据校验不通过");
                }
            }
            if (sb.lastIndexOf("、") == sb.length() - 1) {
                sb.delete(sb.length() - 1, sb.length());
            }
            return sb.toString();
        }
        return null;
    }
}
