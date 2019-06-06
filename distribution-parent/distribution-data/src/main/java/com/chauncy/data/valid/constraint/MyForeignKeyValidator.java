package com.chauncy.data.valid.constraint;

import com.alibaba.fastjson.JSON;
import com.chauncy.data.mapper.IBaseMapper;
import com.chauncy.data.mapper.product.PmGoodsMapper;
import com.chauncy.data.valid.annotation.MyForeignKeyConstraint;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * @Author zhangrt
 * @Date 2019-06-03 15:43
 **/
public class MyForeignKeyValidator implements ConstraintValidator<MyForeignKeyConstraint, Object> {

    @Autowired
    private PmGoodsMapper baseMapper;

    private String tableName="";


    @Override
    public void initialize(MyForeignKeyConstraint constraintAnnotation) {
        tableName = constraintAnnotation.tableName();
    }




    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        //如果是parentid可能是空，要判断非空在外面加注解@Notnull
        if (value==null){
            return true;
        }
        //如果传的是id集合就要一个个验证
        if (value instanceof List){
            List<String> ids= JSON.parseArray(JSON.toJSONString(value),String.class);
            for (String id:ids){
                int count = baseMapper.countById(id, tableName);
                if (count==0){
                    return false;
                }
            }
            return true;

        }
        else {
            int count = baseMapper.countById(value, tableName);
            return count>0;
        }
    }
}
