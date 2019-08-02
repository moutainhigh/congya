package com.chauncy.common.enums.app.gift;

import com.chauncy.common.enums.BaseEnum;
import lombok.Getter;

import java.util.Objects;

/**
 * @Author cheng
 * @create 2019-07-18 12:36
 */
@Getter
public enum GiftTypeEnum implements BaseEnum {

    TOP_UP(1,"充值礼包"),
    NEW_COMER(2,"新人礼包");

    private Integer id;

    private String name;

    GiftTypeEnum(Integer id, String name) {
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
    public static GiftTypeEnum getGiftTypeEnumById(Integer id) {
        for (GiftTypeEnum type : GiftTypeEnum.values()) {
            if (type.getId() == id)
                return type;
        }
        return null;
    }

    //通过名称来获取结果
    public static GiftTypeEnum fromName(String name) {
        for (GiftTypeEnum type : GiftTypeEnum.values()) {
            if (type.getName().equals(name))
                return type;
        }
        return null;
    }

    //通过name获取结果
    public static GiftTypeEnum fromEnumName(String name) {
        for (GiftTypeEnum validCodeEnum : GiftTypeEnum.values()) {
            if (validCodeEnum.name().equals(name))
                return validCodeEnum;
        }
        return null;
    }

    @Override
    public boolean isExist(Object field) {
        return Objects.nonNull(getGiftTypeEnumById(Integer.parseInt(field.toString())));
    }
}
