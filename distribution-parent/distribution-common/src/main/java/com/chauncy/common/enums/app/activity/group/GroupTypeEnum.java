package com.chauncy.common.enums.app.activity.group;

import com.chauncy.common.enums.BaseEnum;
import lombok.Getter;

import java.util.Objects;

/**
 * @Author cheng
 * @create 2019-07-23 11:36
 *
 * 活动分组类型
 */
@Getter
public enum GroupTypeEnum implements BaseEnum {

    REDUCED(1,"满减"),
    INTEGRALS(2,"积分");

    private Integer id;

    private String name;

    GroupTypeEnum(Integer id, String name) {
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
    public static GroupTypeEnum getGroupTypeEnumById(Integer id) {
        for (GroupTypeEnum type : GroupTypeEnum.values()) {
            if (type.getId() == id)
                return type;
        }
        return null;
    }

    //通过名称来获取结果
    public static GroupTypeEnum fromName(String name) {
        for (GroupTypeEnum type : GroupTypeEnum.values()) {
            if (type.getName().equals(name))
                return type;
        }
        return null;
    }

    //通过name获取结果
    public static GroupTypeEnum fromEnumName(String name) {
        for (GroupTypeEnum validCodeEnum : GroupTypeEnum.values()) {
            if (validCodeEnum.name().equals(name))
                return validCodeEnum;
        }
        return null;
    }

    @Override
    public boolean isExist(Object field) {
        return Objects.nonNull(getGroupTypeEnumById(Integer.parseInt(field.toString())));
    }

}
