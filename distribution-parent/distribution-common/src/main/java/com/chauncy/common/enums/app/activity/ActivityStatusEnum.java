package com.chauncy.common.enums.app.activity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.chauncy.common.enums.BaseEnum;
import lombok.Getter;

import java.util.Objects;

/**
 * @Author cheng
 * @create 2019-07-23 17:46
 *
 * 活动状态
 */
@Getter
public enum ActivityStatusEnum implements BaseEnum {


    TO_START(1,"待开始"),
    ONGOING(2,"活动中"),
    REGISTRATION(3,"报名中"),
    HAS_ENDED(4,"已结束");

    @EnumValue
    private Integer id;

    private String name;

    ActivityStatusEnum(Integer id, String name) {
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
    public static ActivityStatusEnum getActivityStatusEnumById(Integer id) {
        for (ActivityStatusEnum type : ActivityStatusEnum.values()) {
            if (type.getId() == id)
                return type;
        }
        return null;
    }

    //通过名称来获取结果
    public static ActivityStatusEnum fromName(String name) {
        for (ActivityStatusEnum type : ActivityStatusEnum.values()) {
            if (type.getName().equals(name))
                return type;
        }
        return null;
    }

    //通过name获取结果
    public static ActivityStatusEnum fromEnumName(String name) {
        for (ActivityStatusEnum validCodeEnum : ActivityStatusEnum.values()) {
            if (validCodeEnum.name().equals(name))
                return validCodeEnum;
        }
        return null;
    }

    @Override
    public boolean isExist(Object field) {
        return Objects.nonNull(getActivityStatusEnumById(Integer.parseInt(field.toString())));
    }


}
