package com.chauncy.common.enums.app.activity.type;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.chauncy.common.enums.BaseEnum;
import lombok.Getter;

import java.util.Objects;

/**
 * @Author cheng
 * @create 2019-07-23 17:46
 *
 * 活动类型
 *
 * 1-满减；2-积分；3-秒杀；4-拼团
 */
@Getter
public enum ActivityTypeEnum implements BaseEnum {

    NON(0,"无活动"),
    REDUCED(1,"满减"),
    INTEGRALS(2,"积分"),
    SECKILL(3,"秒杀"),
    SPELL_GROUP(4,"拼团"),
    SECKILL_ING(5,"秒杀进行中"),
    SPELL_PRE(6,"秒杀待开始(距离当前时间一天)");

    @EnumValue
    private Integer id;

    private String name;

    ActivityTypeEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name;
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
        return Objects.nonNull(fromName(field.toString()));
    }




}
