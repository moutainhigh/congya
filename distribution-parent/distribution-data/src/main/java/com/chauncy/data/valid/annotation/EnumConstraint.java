package com.chauncy.data.valid.annotation;

import com.chauncy.data.valid.constraint.EnumValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @Author zhangrt
 * @Date 2019-06-03 15:39
 **/


@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidator.class)
@Documented
public @interface EnumConstraint {

    String message() default "所传的值在枚举类中不存在！";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 枚举的类文件
     * @return
     */
     Class<?> target();
}
