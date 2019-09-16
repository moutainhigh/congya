package com.chauncy.common.enums.app.activity.evaluate;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.chauncy.common.enums.BaseEnum;
import lombok.Getter;

import java.util.Objects;

/**
 * @Author cheng
 * @create 2019-09-06 17:45
 */
@Getter

public enum EvaluateEnum implements BaseEnum {

    HIGH_PRAISE(1,"好评"),
    MIDDLE_PRAISE(2,"中评"),
    LOW_PRAISE(3,"差评"),
    PICTURE(4,"图片");

    @EnumValue  //这个注解放在数据库存储的字段上
    private Integer id;

    private String name;

    EvaluateEnum(Integer id , String name){
        this.id = id;
        this.name = name;
    }

    //重写toString方法，返回值为显示的值
    @Override
    public String toString(){
        return this.getName();
    }

    //通过ID获取结果
    public static EvaluateEnum fromId(Integer id){
        for (EvaluateEnum type : EvaluateEnum.values()){
            if (type.getId().equals(id))
                return type;
        }
        return null;
    }

    //通过名称来获取结果
    public static EvaluateEnum fromName(String name){
        for (EvaluateEnum type : EvaluateEnum.values()){
            if (type.getName().equals(name)){
                return type;
            }
        }
        return null;
    }

    //通过enumName获取结果
    public static EvaluateEnum fromEnumName(String name){
        for (EvaluateEnum type : EvaluateEnum.values()){
            if (type.name().equals(name))
                return type;
        }
        return null;
    }

    @Override
    public boolean isExist(Object field) {

        if (Integer.parseInt(field.toString()) == 0){
            return true;
        }
        //通过ID判断
        return Objects.nonNull(fromId(Integer.parseInt(field.toString())));

        //通过enumName判断
//        return Objects.nonNull(fromEnumName(field.toString()));
    }
}

