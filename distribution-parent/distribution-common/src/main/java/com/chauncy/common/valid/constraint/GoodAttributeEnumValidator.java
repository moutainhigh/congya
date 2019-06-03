package com.chauncy.common.valid.constraint;

import com.chauncy.common.enums.goods.GoodsAttribute;
import com.chauncy.common.valid.annotation.GoodAttributeTypeConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * @Author zhangrt
 * @Date 2019-06-03 15:43
 **/
public class GoodAttributeEnumValidator implements ConstraintValidator<GoodAttributeTypeConstraint, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        GoodsAttribute goodsAttribute = GoodsAttribute.getGoodsAttributeById(value);
        return Objects.nonNull(goodsAttribute);
    }
}
