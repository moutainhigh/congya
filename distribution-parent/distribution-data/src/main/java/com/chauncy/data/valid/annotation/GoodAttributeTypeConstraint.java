package com.chauncy.data.valid.annotation;

import com.chauncy.data.valid.constraint.GoodAttributeEnumValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @Author zhangrt
 * @Date 2019-06-03 15:39
 **/


@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GoodAttributeEnumValidator.class)
@Documented
public @interface GoodAttributeTypeConstraint {

    String message() default "商品属性不存在，type必须为1-7";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
