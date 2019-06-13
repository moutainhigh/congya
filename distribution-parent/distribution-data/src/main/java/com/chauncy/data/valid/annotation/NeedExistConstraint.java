package com.chauncy.data.valid.annotation;

import com.chauncy.data.valid.constraint.NeedExistValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**

 * @Author zhangrt
 * @Date 2019-06-03 15:39
 **/

@Repeatable(NeedExistConstraint.List.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NeedExistValidator.class)
@Documented

public @interface NeedExistConstraint {
    /**
     * 验证不通过时的报错信息
     * @return
     */
    String message() default "数据库中不存在该数据！";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 数据库表名称
     * @return
     */
    String tableName() ;
     /**isNeedExists为true时，当数据库存在该数据时验证通过（用于验证外键是否存在）
      * isNeedExists为false时，当数据库存在该数据时验证不通过（用于删除时验证数据是否被引用）
      */
    boolean isNeedExists() default true;

    /**
     * 数据库中查找哪个字段  如id：where id=#{id}
     * @return
     */
    String field() default "id";

    /**
     * 支持重复注解
     */
    @Target({ METHOD, FIELD, ANNOTATION_TYPE })
    @Retention(RUNTIME)
    @Documented
     public @interface List {
        NeedExistConstraint[] value();
    }

}
