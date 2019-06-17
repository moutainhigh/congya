package com.chauncy.data.valid.constraint;

import com.alibaba.fastjson.JSON;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.data.mapper.product.PmGoodsMapper;
import com.chauncy.data.valid.annotation.NeedExistConstraint;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

/**检查数据库中是否存在该数据
 * isNeedExists为true时，当数据库存在该数据时验证通过（用于验证外键是否存在）
 * isNeedExists为false时，当数据库存在该数据时验证不通过（用于删除时验证数据是否被引用）
 * @Author zhangrt
 * @Date 2019-06-03 15:43
 **/
public class NeedExistValidator implements ConstraintValidator<NeedExistConstraint, Object> {

    @Autowired
    private PmGoodsMapper baseMapper;

    private String tableName="";

    private boolean isNeedExists;

    private String field;


    @Override
    public void initialize(NeedExistConstraint constraintAnnotation) {
        tableName = constraintAnnotation.tableName();
        isNeedExists=constraintAnnotation.isNeedExists();
        field=constraintAnnotation.field();
    }




    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        //如果是parentid可能是空，要判断非空在外面加注解@Notnull
        if (value==null){
            return true;
        }
        //如果传的是id集合就要一个个验证
        if (value instanceof List ||value.getClass().isArray()){
            //object转list
            List<String> ids= JSON.parseArray(JSON.toJSONString(value),String.class);
            for (String id:ids){
                int count = baseMapper.countById(id, tableName,field);
                if (count==0){
                    //如果数据库不存在
                    if (isNeedExists){
                        return false;
                    }
                }
                else {
                    if (!isNeedExists){
                        return false;
                    }
                }
            }
            return true;

        }
        else {
            int count = baseMapper.countById(value, tableName,field);
            return count>0==isNeedExists;
        }
    }
}
