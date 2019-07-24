package com.chauncy.common.enums.app.activity.type;

import com.chauncy.common.enums.BaseEnum;
import lombok.Getter;

import java.util.Objects;

/**
 * @Author cheng
 * @create 2019-07-23 17:46
 *
 * 活动类型
 */
@Getter
public enum ActivityTypeEnum implements BaseEnum {


    REDUCED(1,"满减"),
    INTEGRALS(2,"积分"),
    SECKILL(3,"秒杀"),
    SPRLL_GROUP(4,"拼团");

    private Integer id;

    private String name;

    ActivityTypeEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString(){
        return this.id + "_" + this.name;
    }

    public static String value(String name){
        return name;
    }

    //通过Id获取结果
    public static ActivityTypeEnum getActivityTypeEnumById(Integer id) {
        for (ActivityTypeEnum type : ActivityTypeEnum.values()) {
            if (type.getId() == id)
                return type;
        }
        return null;
    }

    //通过名称来获取结果
    public static ActivityTypeEnum fromName(String name) {
        for (ActivityTypeEnum type : ActivityTypeEnum.values()) {
            if (type.getName().equals(name))
                return type;
        }
        return null;
    }

    //通过name获取结果
    public static ActivityTypeEnum fromEnumName(String name) {
        for (ActivityTypeEnum validCodeEnum : ActivityTypeEnum.values()) {
            if (validCodeEnum.name().equals(name))
                return validCodeEnum;
        }
        return null;
    }

    @Override
    public boolean isExist(Object field) {
        return Objects.nonNull(getActivityTypeEnumById(Integer.parseInt(field.toString())));
    }


}