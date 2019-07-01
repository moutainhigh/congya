package com.chauncy.data.valid.constraint;

import com.alibaba.fastjson.JSON;
import com.chauncy.common.enums.system.ResultCode;
import com.chauncy.common.exception.sys.ServiceException;
import com.chauncy.common.util.StringUtils;
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

    private String concatWhereSql;


    @Override
    public void initialize(NeedExistConstraint constraintAnnotation) {
        tableName = constraintAnnotation.tableName();
        isNeedExists=constraintAnnotation.isNeedExists();
        field=constraintAnnotation.field();
        concatWhereSql=constraintAnnotation.concatWhereSql();
    }




    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        //如果是parentid可能是空，要判断非空在外面加注解@Notnull
        if (value==null|| StringUtils.isBlank(value.toString())){
            return true;
        }
        //long类型特殊处理，前端有时候空的会传0
        if (value instanceof Long){
            if (Long.parseLong(value.toString())==0){
                return true;
            }
        }
        //如果传的是集合就要一个个验证
        if (value instanceof List ||value.getClass().isArray()){
            //object转list
            List<String> ids= JSON.parseArray(JSON.toJSONString(value),String.class);
            for (String id:ids){
                int count = baseMapper.countById(id, tableName,field,concatWhereSql);
                if (count==0){
                    constraintValidatorContext.disableDefaultConstraintViolation();//禁用默认的message的值
                    //重新添加错误提示语句
                    constraintValidatorContext
                            .buildConstraintViolationWithTemplate(String.format("%s为【%s】在数据库中不存在！",field,id)).addConstraintViolation();
                    return false;
                   /* //如果数据库不存在
                    if (isNeedExists){
                        throw new ServiceException(ResultCode.PARAM_ERROR,"%s为【%s】在数据库中不存在！",field,id);
                    }*/
                }
                else {
                    if (!isNeedExists){
                        constraintValidatorContext.disableDefaultConstraintViolation();//禁用默认的message的值
                        //重新添加错误提示语句
                        constraintValidatorContext
                                .buildConstraintViolationWithTemplate(String.format("%s为【%s】在数据库已存在！不允许删除！",field,id)).addConstraintViolation();
                        return false;
                    }
                }
            }
            return true;

        }
        else {
            int count = baseMapper.countById(value, tableName,field,concatWhereSql);
            return count>0==isNeedExists;
        }
    }
}
