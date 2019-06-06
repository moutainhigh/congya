package com.chauncy.data.valid.annotation;

import com.chauncy.data.valid.constraint.EnumValidator;
import com.chauncy.data.valid.constraint.MyForeignKeyValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @Author zhangrt
 * @Date 2019-06-03 15:39
 **/


@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MyForeignKeyValidator.class)
@Documented
public @interface MyForeignKeyConstraint {

    String message() default "数据库中不存在该数据！";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String tableName() ;
}
