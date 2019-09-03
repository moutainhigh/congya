package com.chauncy.common.enums.app.advice;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.chauncy.common.enums.BaseEnum;
import lombok.Getter;

import java.util.Objects;

/**
 * @Author cheng
 * @create 2019-07-17 22:45
 *
 *  广告关联类型枚举类
 * 1->店铺分类,2->品牌,3->商品,4->商品三级分类,5->店铺,6->商品一级分类，7-资讯分类
 */
@Getter
public enum AssociationTypeEnum implements BaseEnum {

    STORE_CLASSIFICATION(1,"店铺分类"),

    BRAND(2,"品牌"),

    Goods(3,"商品"),

    THIRD_CLASSIFICATION(4,"商品三级分类"),

    STORE(5,"店铺"),

    FIRST_CLASSIFICATION(6,"商品一级分类"),

    INFORMATION_CLASSIFICATION(7,"资讯分类");

    @EnumValue
    private Integer id;

    private String name;

    AssociationTypeEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    //通过Id获取结果
    public static AssociationTypeEnum getAdviceTypeEnum(Integer id) {
        for (AssociationTypeEnum type : AssociationTypeEnum.values()) {
            if (type.getId().equals(id) )
                return type;
        }
        return null;
    }

    //通过名称来获取结果
    public static AssociationTypeEnum fromName(String name) {
        for (AssociationTypeEnum type : AssociationTypeEnum.values()) {
            if (type.getName().equals(name))
                return type;
        }
        return null;
    }

    //通过name获取结果
    public static AssociationTypeEnum fromEnumName(String name) {
        for (AssociationTypeEnum validCodeEnum : AssociationTypeEnum.values()) {
            if (validCodeEnum.name().equals(name))
                return validCodeEnum;
        }
        return null;
    }

    @Override
    public boolean isExist(Object field) {
        return Objects.nonNull(getAdviceTypeEnum(Integer.parseInt(field.toString())));
    }
}
