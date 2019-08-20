package com.chauncy.common.enums.app.advice;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.chauncy.common.enums.BaseEnum;
import com.chauncy.common.util.JSONUtils;
import lombok.Getter;

import java.util.Objects;

/**
 * @Author cheng
 * @create 2019-07-17 22:45
 *
 *  广告类型枚举类
 *
 * 1-"图文详情",2-"资讯",3-"店铺",4-"商品"
 */
@Getter
public enum AdviceTypeEnum implements BaseEnum {

    HTML_DETAIL(1,"图文详情"),

    INFORMATION(2,"资讯"),

    STROE(3,"店铺"),

    GOODS(4,"商品");

    @EnumValue
    private Integer id;

    private String name;

    AdviceTypeEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    //通过Id获取结果
    public static AdviceTypeEnum getAdviceTypeEnum(Integer id) {
        for (AdviceTypeEnum type : AdviceTypeEnum.values()) {
            if (type.getId().equals(id) )
                return type;
        }
        return null;
    }

    //通过名称来获取结果
    public static AdviceTypeEnum fromName(String name) {
        for (AdviceTypeEnum type : AdviceTypeEnum.values()) {
            if (type.getName().equals(name))
                return type;
        }
        return null;
    }

    //通过name获取结果
    public static AdviceTypeEnum fromEnumName(String name) {
        for (AdviceTypeEnum validCodeEnum : AdviceTypeEnum.values()) {
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
